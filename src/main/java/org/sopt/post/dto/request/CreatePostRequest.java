package org.sopt.post.dto.request;

import org.sopt.post.domain.Category;

public record CreatePostRequest(
        String title,
        String content,
        Category category,
        String author
) {
}
