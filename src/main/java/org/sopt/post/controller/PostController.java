package org.sopt.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.global.response.ApiResponse;
import org.sopt.post.code.PostSuccessCode;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "POST", description = "게시글 API")
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
    @Operation(
            summary = "게시글 작성",
            description = "제목, 본문, 카테고리, 작성자를 입력받아 게시글을 작성합니다."
    )
    public ResponseEntity<ApiResponse<Void>> createPost(
            @RequestBody CreatePostRequest request
    ) {
        postService.createPost(request);
        return ApiResponse.toResponseEntity(PostSuccessCode.POST_CREATED, null);
    }

    @GetMapping
    @Operation(
            summary = "게시글 목록 조회",
            description = "페이지 번호에 해당하는 게시글을 최신순으로 조회합니다. 페이지 번호는 1부터 시작합니다."
    )
    public ResponseEntity<ApiResponse<List<PostResponse>>> readPosts(
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        return ApiResponse.toResponseEntity(PostSuccessCode.POST_LIST_READ, postService.readPosts(page));
    }

    @GetMapping(path = "/{postId}")
    @Operation(
            summary = "게시글 단건 조회",
            description = "게시글 상세 정보를 조회합니다. 조회할 때마다 조회수가 1 증가합니다."
    )
    public ResponseEntity<ApiResponse<PostResponse>> readPost(
            @PathVariable(name = "postId") Long postId
    ) {
        return ApiResponse.toResponseEntity(PostSuccessCode.POST_READ, postService.readPost(postId));
    }

    @PutMapping(path = "/{postId}")
    @Operation(
            summary = "게시글 수정",
            description = "게시글 번호에 해당하는 게시글의 제목과 본문을 수정합니다."
    )

    public ResponseEntity<ApiResponse<Void>> updatePost(
            @RequestBody UpdatePostRequest request,
            @PathVariable(name = "postId") Long postId
    ) {
        postService.updatePost(postId, request);
        return ApiResponse.toResponseEntity(PostSuccessCode.POST_UPDATED, null);
    }

    @DeleteMapping(path = "/{postId}")
    @Operation(
            summary = "게시글 삭제",
            description = "게시글 번호에 해당하는 게시글을 삭제합니다."
    )
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable(name = "postId") Long postId
    ) {
        postService.deletePost(postId);
        return ApiResponse.toResponseEntity(PostSuccessCode.POST_DELETED, null);
    }
}