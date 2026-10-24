package org.sopt.domain.member.code;

import org.sopt.global.code.SuccessBaseCode;
import org.springframework.http.HttpStatus;

public enum MemberSuccessCode implements SuccessBaseCode {
    MEMBER_CREATED(HttpStatus.CREATED, "회원이 생성되었습니다."),
    MEMBER_DELETED(HttpStatus.OK, "회원이 삭제되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    MemberSuccessCode(HttpStatus httpStatus, String message) {
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
