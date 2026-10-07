package org.sopt.post.exception;

import org.sopt.global.exception.BusinessException;
import org.sopt.post.code.PostErrorCode;

public class PostValidationException extends BusinessException {
    public PostValidationException(PostErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
