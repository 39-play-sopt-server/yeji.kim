package org.sopt.domain.auth.exception;

import org.sopt.domain.auth.code.AuthErrorCode;
import org.sopt.global.exception.BusinessException;

public class AuthValidationException extends BusinessException {
    public AuthValidationException(AuthErrorCode errorCode) {
        super(errorCode);
    }
}
