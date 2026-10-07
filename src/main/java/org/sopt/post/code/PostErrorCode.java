package org.sopt.post.code;

public enum PostErrorCode {
    POST_NOT_FOUND("게시글이 존재하지 않습니다."),
    POST_TITLE_EMPTY("제목은 비어 있을 수 없습니다."),
    POST_CONTENT_EMPTY("본문은 비어 있을 수 없습니다.");

    private final String message;

    PostErrorCode(String message) {this.message = message;}

    public String getMessage() {
        return message;
    }
}
