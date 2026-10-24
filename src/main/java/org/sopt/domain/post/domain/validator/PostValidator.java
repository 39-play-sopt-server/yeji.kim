package org.sopt.domain.post.domain.validator;

import org.sopt.domain.post.code.PostErrorCode;
import org.sopt.domain.post.exception.PostValidationException;

public class PostValidator {
    public static void validate(String title, String content) {
        if (title == null || title.isBlank()) {
            throw new PostValidationException(PostErrorCode.POST_TITLE_EMPTY);
        }
        if (content == null || content.isBlank()) {
            throw new PostValidationException(PostErrorCode.POST_CONTENT_EMPTY);
        }
    }
}
