package org.sopt.post.dto.response;

import org.sopt.post.domain.Category;
import org.sopt.post.domain.Post;

public record PostResponse(
        Long id,
        String title,
        String content,
        Category category,
        String createdAt,
        String author
) {
    public PostResponse(Post post) {
        this(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCategory(),
                post.getCreatedAt(),
                post.getAuthor()
        );
    }
}
