package org.sopt.domain.post.code;

import org.sopt.global.code.SuccessBaseCode;
import org.springframework.http.HttpStatus;

public enum PostSuccessCode implements SuccessBaseCode {
    POST_CREATED(HttpStatus.CREATED, "게시글이 작성되었습니다."),
    POST_LIST_READ(HttpStatus.OK, "게시글 목록을 조회했습니다."),
    POST_READ(HttpStatus.OK, "게시글을 조회했습니다."),
    POST_UPDATED(HttpStatus.OK, "게시글이 수정되었습니다."),
    POST_DELETED(HttpStatus.OK, "게시글이 삭제되었습니다."),
    POST_LIKED(HttpStatus.OK, "게시글에 좋아요를 눌렀습니다."),
    POST_LIKE_CANCELED(HttpStatus.OK, "게시글 좋아요를 취소했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    PostSuccessCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
