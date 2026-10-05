package org.sopt.post.controller;

import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.service.PostService;

import java.util.List;

public class PostController {
    private final PostService postService;

    public PostController(
            PostService postService
    ) {
        this.postService = postService;
    }

    public void createPost(CreatePostRequest request) {
        postService.createPost(request);
    }

    public List<PostResponse> readPosts() {
        return postService.readPosts();
    }

    public PostResponse readPost(Long id) {
        return postService.readPost(id);
    }

    public void updatePost(UpdatePostRequest request) {
        postService.updatePost(request);
    }

    public void deletePost(Long id) {
        postService.deletePost(id);
    }
}