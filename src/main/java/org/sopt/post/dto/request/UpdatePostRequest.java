package org.sopt.post.dto.request;

public record UpdatePostRequest(
        Long id,
        String title,
        String content
) {
}
