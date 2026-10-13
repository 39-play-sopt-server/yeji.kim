package org.sopt.post.controller;

import org.sopt.global.response.ApiResponse;
import org.sopt.post.code.PostSuccessCode;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController implements PostControllerDocs{
    private final PostService postService;

    public PostController(
            PostService postService
    ) {
        this.postService = postService;
    }

    @PostMapping
    @Override
    public ResponseEntity<ApiResponse<Void>> createPost(
            @RequestBody CreatePostRequest request
    ) {
        postService.createPost(request);
        return ApiResponse.toResponseEntity(PostSuccessCode.POST_CREATED, null);
    }

    @GetMapping
    @Override
    public ResponseEntity<ApiResponse<List<PostResponse>>> readPosts(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return ApiResponse.toResponseEntity(PostSuccessCode.POST_LIST_READ, postService.readPosts(page, size));
    }

    @GetMapping(path = "/{postId}")
    @Override
    public ResponseEntity<ApiResponse<PostResponse>> readPost(
            @PathVariable(name = "postId") Long postId
    ) {
        return ApiResponse.toResponseEntity(PostSuccessCode.POST_READ, postService.readPost(postId));
    }

    @PutMapping(path = "/{postId}")
    @Override
    public ResponseEntity<ApiResponse<Void>> updatePost(
            @RequestBody UpdatePostRequest request,
            @PathVariable(name = "postId") Long postId
    ) {
        postService.updatePost(postId, request);
        return ApiResponse.toResponseEntity(PostSuccessCode.POST_UPDATED, null);
    }

    @DeleteMapping(path = "/{postId}")
    @Override
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable(name = "postId") Long postId
    ) {
        postService.deletePost(postId);
        return ApiResponse.toResponseEntity(PostSuccessCode.POST_DELETED, null);
    }
}