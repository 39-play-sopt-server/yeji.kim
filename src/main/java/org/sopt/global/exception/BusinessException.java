package org.sopt.global.exception;

import org.sopt.global.code.ErrorBaseCode;

public class BusinessException extends RuntimeException {
    private final ErrorBaseCode errorCode;

    public BusinessException(ErrorBaseCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorBaseCode getErrorCode() {
        return this.errorCode;
    }
}
