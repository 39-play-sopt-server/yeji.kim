package org.sopt.post.validator;

import org.sopt.post.exception.PostValidationException;

public class PostValidator {
    public void validate(String title, String content) {
        if (title.isBlank()) {
            throw new PostValidationException("제목은 비어 있을 수 없습니다.");
        }
        if (content.isBlank()) {
            throw new PostValidationException("본문은 비어 있을 수 없습니다.");
        }
    }
}
