package org.sopt.domain.post.service;

import org.sopt.domain.member.domain.entity.Member;
import org.sopt.domain.member.exception.MemberNotFoundException;
import org.sopt.domain.member.repository.MemberRepository;
import org.sopt.domain.post.code.PostErrorCode;
import org.sopt.domain.post.domain.entity.Category;
import org.sopt.domain.post.domain.entity.Post;
import org.sopt.domain.post.dto.request.CreatePostRequest;
import org.sopt.domain.post.dto.request.UpdatePostRequest;
import org.sopt.domain.post.dto.response.PostResponse;
import org.sopt.domain.post.exception.PostNotFoundException;
import org.sopt.domain.post.exception.PostValidationException;
import org.sopt.domain.post.repository.PostRepository;
import org.sopt.domain.post.repository.specification.PostSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private static final int MAX_PAGE_SIZE = 100;

    public PostService(
            PostRepository postRepository,
            MemberRepository memberRepository
    ) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public synchronized void createPost(CreatePostRequest request) {

        Category category = Category.from(request.category());
        Member member = memberRepository.findById(request.authorId())
                .orElseThrow(MemberNotFoundException::new);

        Post post = new Post(
                request.title(),
                request.content(),
                category,
                member
        );

        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public synchronized List<PostResponse> readPosts(
            int page,
            int size,
            Long authorId,
            String category,
            String title
    ) {
        if (page < 1) {
            throw new PostValidationException(PostErrorCode.POST_PAGE_INVALID);
        }

        if (size < 1 || size > MAX_PAGE_SIZE) {
            throw new PostValidationException(PostErrorCode.POST_SIZE_INVALID);
        }

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Category selectedCategory =
                category == null ? null : Category.from(category);
        Specification<Post> spec =
                ((root, query, criteriaBuilder) ->
                        criteriaBuilder.conjunction());

        if (authorId != null) {
            spec = spec.and(PostSpecification.hasAuthorId(authorId));
        }

        if (selectedCategory != null) {
            spec = spec.and(PostSpecification.hasCategory(selectedCategory));
        }

        if (title != null && !title.isBlank()) {
            spec = spec.and(PostSpecification.titleMatches(title));
        }

        List<PostResponse> responses = new ArrayList<>();
        List<Post> posts = postRepository.findAll(spec, pageable).getContent();

        for (Post post : posts) {
            PostResponse response = new PostResponse(post, post.getLikeCount());
            responses.add(response);
        }

        return responses;
    }

    @Transactional(readOnly = true)
    public synchronized PostResponse readPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        return new PostResponse(post, post.getLikeCount());
    }

    @Transactional
    public synchronized void updatePost(Long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        post.update(request.title(), request.content());
    }

    @Transactional
    public synchronized void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        postRepository.delete(post);
    }
}
