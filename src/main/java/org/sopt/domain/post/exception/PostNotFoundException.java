package org.sopt.domain.post.exception;

import org.sopt.global.exception.BusinessException;
import org.sopt.domain.post.code.PostErrorCode;

public class PostNotFoundException extends BusinessException {
    public PostNotFoundException() {
        super(PostErrorCode.POST_NOT_FOUND);
    }
}
