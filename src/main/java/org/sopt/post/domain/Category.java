package org.sopt.post.domain;

import org.sopt.post.code.PostErrorCode;
import org.sopt.post.exception.PostValidationException;

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
