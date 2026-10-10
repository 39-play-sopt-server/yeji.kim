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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostService {
    private final PostRepository postRepository;

    public PostService(
            PostRepository postRepository
    ) {
        this.postRepository = postRepository;
    }

    private long nextId = 1;

    public void createPost(CreatePostRequest request) {

        String createdAt = LocalDateTime.now().toString();
        Category category;
        try {
            category = Category.valueOf(request.category());
        } catch (IllegalArgumentException e) {
            throw new PostValidationException(PostErrorCode.POST_CATEGORY_INVALID);
        }
        Post post = new Post(
                nextId,
                request.title(),
                request.content(),
                category,
                createdAt,
                request.author()
        );
        nextId += 1;

        postRepository.save(post);
    }

    public List<PostResponse> readPosts() {
        List<PostResponse> responses = new ArrayList<>();
        List<Post> posts = postRepository.findAll();

        for (Post post : posts) {
            PostResponse response = new PostResponse(post);
            responses.add(response);
        }

        return responses;
    }

    public PostResponse readPost(Long id) {
        Post post = postRepository.findById(id);

        if (post == null) {
            throw new PostNotFoundException();
        }

        return new PostResponse(post);
    }

    public void updatePost(UpdatePostRequest request) {
        Post post = postRepository.findById(request.id());

        if (post == null) {
            throw new PostNotFoundException();
        }

        post.update(request.title(), request.content());

        postRepository.updatePost(post);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id);

        if (post == null) {
            throw new PostNotFoundException();
        }

        postRepository.deletePost(id);
    }
}
