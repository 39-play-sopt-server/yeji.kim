package org.sopt.post.controller;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.response.ApiResponse;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(
            PostService postService
    ) {
        this.postService = postService;
    }

    @PostMapping
    public ApiResponse<Void> createPost(
            @RequestBody CreatePostRequest request) {
        try {
            postService.createPost(request);
            return ApiResponse.success(null);
        } catch (BusinessException e) {
            return ApiResponse.failure(e.getMessage());
        }
    }

    @GetMapping
    public ApiResponse<List<PostResponse>> readPosts(
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        return ApiResponse.success(postService.readPosts());
    }

    @GetMapping(path = "/{postId}")
    public ApiResponse<PostResponse> readPost(
            @PathVariable(name = "postId") Long postId
    ) {
        try {
            return ApiResponse.success(postService.readPost(postId));
        } catch (BusinessException e) {
            return ApiResponse.failure(e.getMessage());
        }
    }

    @PutMapping(path = "/{postId}")
    public ApiResponse<Void> updatePost(
            @RequestBody UpdatePostRequest request,
            @PathVariable(name = "postId") Long postId) {
        try {
            postService.updatePost(postId, request);
            return ApiResponse.success(null);
        } catch (BusinessException e) {
            return ApiResponse.failure(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{postId}")
    public ApiResponse<Void> deletePost(
            @PathVariable(name = "postId") Long postId
    ) {
        try {
            postService.deletePost(postId);
            return ApiResponse.success(null);
        } catch (BusinessException e) {
            return ApiResponse.failure(e.getMessage());
        }
    }
}