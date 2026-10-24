package org.sopt.domain.post.controller;

import org.sopt.domain.post.code.PostSuccessCode;
import org.sopt.domain.post.service.PostLikeService;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/posts/{postId}/likes")
public class PostLikeController implements PostLikeControllerDocs{
    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @PostMapping
    @Override
    public ResponseEntity<ApiResponse<Void>> likePost(
            @PathVariable(name = "postId") Long postId,
            @RequestParam(name = "memberId") Long memberId
    ) {
        postLikeService.likePost(postId, memberId);
        return ApiResponse.toResponseEntity(PostSuccessCode.POST_LIKED, null);
    }

    @DeleteMapping
    @Override
    public ResponseEntity<ApiResponse<Void>> cancelPostLike(
            @PathVariable(name = "postId") Long postId,
            @RequestParam(name = "memberId") Long memberId
    ) {
        postLikeService.cancelPostLike(postId, memberId);
        return ApiResponse.toResponseEntity(PostSuccessCode.POST_LIKE_CANCELED, null);
    }
}
