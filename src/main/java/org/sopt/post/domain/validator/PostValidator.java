package org.sopt.post.domain.validator;

import org.sopt.post.code.PostErrorCode;
import org.sopt.post.exception.PostValidationException;

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
