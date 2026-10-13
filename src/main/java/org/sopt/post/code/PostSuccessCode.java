package org.sopt.post.code;

import org.sopt.global.code.SuccessBaseCode;

public enum PostSuccessCode implements SuccessBaseCode {
    POST_CREATED("게시글이 작성되었습니다."),
    POST_LIST_READ("게시글 목록을 조회했습니다."),
    POST_READ("게시글을 조회했습니다."),
    POST_UPDATED("게시글이 수정되었습니다."),
    POST_DELETED("게시글이 삭제되었습니다.");

    private final String message;

    PostSuccessCode(String message) {this.message = message;}

    public String getMessage() {
        return message;
    }
}
