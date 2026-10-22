package org.sopt.post.service;

import org.sopt.post.code.PostErrorCode;
import org.sopt.post.domain.Category;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.exception.PostValidationException;
import org.sopt.post.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private static final int MAX_PAGE_SIZE = 100;

    public PostService(
            PostRepository postRepository
    ) {
        this.postRepository = postRepository;
    }

    public synchronized void createPost(CreatePostRequest request) {

        LocalDateTime createdAt = LocalDateTime.now();
        Category category = Category.from(request.category());

        Post post = new Post(
                request.title(),
                request.content(),
                category,
                createdAt,
                request.author()
        );

        postRepository.save(post);
    }

    public synchronized List<PostResponse> readPosts(int page, int size, String category) {
        if (page < 1) {
            throw new PostValidationException(PostErrorCode.POST_PAGE_INVALID);
        }

        if (size < 1 || size > MAX_PAGE_SIZE) {
            throw new PostValidationException(PostErrorCode.POST_SIZE_INVALID);
        }

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Category selectedCategory =
                category == null ? null : Category.from(category);

        List<PostResponse> responses = new ArrayList<>();
        List<Post> posts =
                selectedCategory == null
                        ? postRepository.findAll(pageable).getContent()
                        : postRepository.findByCategory(selectedCategory, pageable);

        for (Post post : posts) {
            PostResponse response = new PostResponse(post);
            responses.add(response);
        }

        return responses;
    }

    public synchronized PostResponse readPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        return new PostResponse(post);
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
