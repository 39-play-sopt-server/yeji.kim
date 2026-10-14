package org.sopt.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.global.response.ApiResponse;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "POST", description = "게시글 API")
public interface PostControllerDocs {

    @Operation(
            summary = "게시글 작성",
            description = "제목, 본문, 카테고리, 작성자를 입력받아 게시글을 작성합니다."
    )
    ResponseEntity<ApiResponse<Void>> createPost(
            @RequestBody CreatePostRequest request
    );

    @Operation(
            summary = "게시글 목록 조회",
            description = "페이지 번호에 해당하는 게시글을 최신순으로 조회합니다. 페이지 번호는 1부터 시작합니다."
    )
    ResponseEntity<ApiResponse<List<PostResponse>>> readPosts(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "category", required = false) String category
    );

    @Operation(
            summary = "게시글 단건 조회",
            description = "게시글 상세 정보를 조회합니다. 조회할 때마다 조회수가 1 증가합니다."
    )
    ResponseEntity<ApiResponse<PostResponse>> readPost(
            @PathVariable(name = "postId") Long postId
    );

    @Operation(
            summary = "게시글 수정",
            description = "게시글 번호에 해당하는 게시글의 제목과 본문을 수정합니다."
    )
    ResponseEntity<ApiResponse<Void>> updatePost(
            @RequestBody UpdatePostRequest request,
            @PathVariable(name = "postId") Long postId
    );

    @Operation(
            summary = "게시글 삭제",
            description = "게시글 번호에 해당하는 게시글을 삭제합니다."
    )
    ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable(name = "postId") Long postId
    );
}
