package org.sopt.post.dto.response;

import org.sopt.post.domain.entity.Category;
import org.sopt.post.domain.entity.Post;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String title,
        String content,
        Category category,
        LocalDateTime createdAt,
        String author,
        long viewCount
) {
    public PostResponse(Post post) {
        this(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCategory(),
                post.getCreatedAt(),
                post.getAuthor(),
                post.getViewCount()
        );
    }
}
