package org.sopt.domain.post.service;

import org.sopt.domain.member.domain.entity.Member;
import org.sopt.domain.member.exception.MemberNotFoundException;
import org.sopt.domain.member.repository.MemberRepository;
import org.sopt.domain.post.domain.entity.Post;
import org.sopt.domain.post.domain.entity.PostLike;
import org.sopt.domain.post.exception.PostNotFoundException;
import org.sopt.domain.post.repository.PostLikeRepository;
import org.sopt.domain.post.repository.PostRepository;
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
    public void likePost(Long postId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (postLikeRepository.existsByMemberIdAndPostId(memberId, postId)) {
            return;
        }

        PostLike postLike = new PostLike(member, post);

        postLikeRepository.save(postLike);
    }

    @Transactional
    public void cancelPostLike(Long postId, Long memberId) {
        memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        Optional<PostLike> postLike = postLikeRepository.findByMemberIdAndPostId(memberId, postId);

        if (postLike.isEmpty()) {
            return;
        }

        postLikeRepository.delete(postLike.get());
    }
}
