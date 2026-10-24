package org.sopt.domain.post.service;

import org.sopt.domain.member.domain.entity.Member;
import org.sopt.domain.member.exception.MemberNotFoundException;
import org.sopt.domain.member.repository.MemberRepository;
import org.sopt.domain.post.domain.entity.Post;
import org.sopt.domain.post.domain.entity.PostLike;
import org.sopt.domain.post.exception.PostNotFoundException;
import org.sopt.domain.post.repository.PostLikeRepository;
import org.sopt.domain.post.repository.PostRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostLikeService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;

    public PostLikeService(
            PostRepository postRepository,
            MemberRepository memberRepository,
            PostLikeRepository postLikeRepository
    ) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.postLikeRepository = postLikeRepository;
    }

    @Transactional
    @Retryable(
            includes = {
                    OptimisticLockingFailureException.class,
                    PessimisticLockingFailureException.class
            },
            maxRetries = 3,
            delay = 50
    )
    public void likePost(Long postId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (postLikeRepository.existsByMemberIdAndPostId(memberId, postId)) {
            return;
        }

        PostLike postLike = new PostLike(member, post);

        post.increaseLikeCount();
        // 변경 SQL을 DB에 실행 -> 저장이 실패하면 같은 트랜잭션이라 증가도 롤백 됨
        postRepository.flush();
        postLikeRepository.save(postLike);
    }

    @Transactional
    @Retryable(
            includes = {
                    OptimisticLockingFailureException.class,
                    PessimisticLockingFailureException.class
            },
            maxRetries = 3,
            delay = 50
    )
    public void cancelPostLike(Long postId, Long memberId) {
        memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        Optional<PostLike> postLike = postLikeRepository.findByMemberIdAndPostId(memberId, postId);

        if (postLike.isEmpty()) {
            return;
        }
        post.decreaseLikeCount();
        postRepository.flush();
        postLikeRepository.delete(postLike.get());
    }
}
