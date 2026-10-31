package org.sopt.domain.post.dto.response;

import org.sopt.domain.post.domain.entity.Category;
import org.sopt.domain.post.domain.entity.Post;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String title,
        String content,
        Category category,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String authorName,
        long viewCount,
        long likeCount
) {
    public PostResponse(Post post, long likeCount) {
        this(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCategory(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getAuthor().getName(),
                post.getViewCount(),
                likeCount
        );
    }
}
