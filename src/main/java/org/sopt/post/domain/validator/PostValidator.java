package org.sopt.post.domain.validator;

import org.sopt.post.code.PostErrorCode;
import org.sopt.post.exception.PostValidationException;

public class PostValidator {
    public static void validate(String title, String content) {
        if (title.isBlank()) {
            throw new PostValidationException(PostErrorCode.POST_TITLE_EMPTY);
        }
        if (content.isBlank()) {
            throw new PostValidationException(PostErrorCode.POST_CONTENT_EMPTY);
        }
    }
}
