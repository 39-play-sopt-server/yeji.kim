package org.sopt.global.code;

public enum SuccessCode implements SuccessBaseCode{

    SUCCESS("요청이 성공했습니다.");

    private final String message;

    SuccessCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
