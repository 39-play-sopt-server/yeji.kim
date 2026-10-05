package org.sopt.post.controller;

import org.sopt.global.response.ApiResponse;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.exception.PostValidationException;
import org.sopt.post.service.PostService;

import java.util.List;

public class PostController {
    private final PostService postService;

    public PostController(
            PostService postService
    ) {
        this.postService = postService;
    }

    public ApiResponse<Void> createPost(CreatePostRequest request) {
        try {
            postService.createPost(request);
            return ApiResponse.success(null);
        } catch (PostValidationException e) {
            return ApiResponse.failure(e.getMessage());
        }
    }

    public ApiResponse<List<PostResponse>> readPosts() {
        return ApiResponse.success(postService.readPosts());
    }

    public ApiResponse<PostResponse> readPost(Long id) {
        try {
            return ApiResponse.success(postService.readPost(id));
        } catch (PostNotFoundException e) {
            return ApiResponse.failure(e.getMessage());
        }
    }

    public ApiResponse<Void> updatePost(UpdatePostRequest request) {
        try {
            postService.updatePost(request);
            return ApiResponse.success(null);
        } catch (PostNotFoundException | PostValidationException e) {
            return ApiResponse.failure(e.getMessage());
        }
    }

    public ApiResponse<Void> deletePost(Long id) {
        try {
            postService.deletePost(id);
            return ApiResponse.success(null);
        } catch (PostNotFoundException e) {
            return ApiResponse.failure(e.getMessage());
        }
    }
}