package org.sopt.domain.post.domain.entity;

import org.sopt.domain.post.code.PostErrorCode;
import org.sopt.domain.post.exception.PostValidationException;

public enum Category {
    HOT,
    FREE,
    SECRET;

    public static Category from(String value) {
        if (value == null) {
            throw new PostValidationException(PostErrorCode.POST_CATEGORY_INVALID);
        }

        try {
            return Category.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new PostValidationException(PostErrorCode.POST_CATEGORY_INVALID);
        }
    }
}
