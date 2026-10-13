package org.sopt.post.code;

import org.sopt.global.code.ErrorBaseCode;
import org.springframework.http.HttpStatus;

public enum PostErrorCode implements ErrorBaseCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."),
    POST_TITLE_EMPTY(HttpStatus.BAD_REQUEST, "제목은 비어 있을 수 없습니다."),
    POST_CONTENT_EMPTY(HttpStatus.BAD_REQUEST, "본문은 비어 있을 수 없습니다."),
    POST_CATEGORY_INVALID(HttpStatus.BAD_REQUEST, "존재하지 않는 카테고리입니다."),
    POST_PAGE_INVALID(HttpStatus.BAD_REQUEST, "페이지 번호는 1 이상이어야 합니다."),
    POST_SIZE_INVALID(HttpStatus.BAD_REQUEST, "페이지 크기는 1 이상이어야 합니다.");

    private final HttpStatus httpStatus;
    private final String message;

    PostErrorCode(HttpStatus httpStatus, String message) {
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
