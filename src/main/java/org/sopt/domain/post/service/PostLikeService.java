package org.sopt.domain.post.service;

import org.sopt.domain.member.repository.MemberRepository;
import org.sopt.domain.post.repository.PostLikeRepository;
import org.sopt.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;

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
}
