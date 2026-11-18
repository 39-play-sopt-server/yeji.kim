package org.sopt.domain.auth.exception;

import org.sopt.domain.auth.code.AuthErrorCode;
import org.sopt.global.exception.BusinessException;

public class InvalidRefreshTokenException extends BusinessException {
    public InvalidRefreshTokenException(AuthErrorCode errorCode) {
        super(errorCode);
    }
}
