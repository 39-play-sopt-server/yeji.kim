package org.sopt.post.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreatePostRequest(
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank String category,
        @NotBlank String author
) {
}
