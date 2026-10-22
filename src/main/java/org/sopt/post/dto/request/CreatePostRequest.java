package org.sopt.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePostRequest(
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank String category,
        @NotNull Long authorId
) {
}
