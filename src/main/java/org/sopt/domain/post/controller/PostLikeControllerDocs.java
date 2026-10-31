package org.sopt.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "POST_LIKE", description = "좋아요 API")
public interface PostLikeControllerDocs {

    @Operation(
            summary = "게시글 좋아요",
            description = "회원 ID와 게시글 ID로 좋아요를 등록합니다. 이미 좋아요를 누른 경우 기존 상태를 유지합니다."
    )
    ResponseEntity<ApiResponse<Void>> likePost(
            @PathVariable(name = "postId") Long postId,
            @RequestParam(name = "memberId") Long memberId
    );

    @Operation(
            summary = "게시글 좋아요 취소",
            description = "회원 ID와 게시글 ID로 좋아요를 취소합니다. 좋아요 기록이 없는 경우 기존 상태를 유지합니다."
    )
    ResponseEntity<ApiResponse<Void>> cancelPostLike(
            @PathVariable(name = "postId") Long postId,
            @RequestParam(name = "memberId") Long memberId
    );
}
