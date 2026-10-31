package org.sopt.domain.post.repository.specification;

import org.sopt.domain.post.domain.entity.Category;
import org.sopt.domain.post.domain.entity.Post;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {
    public static Specification<Post> hasAuthorId(Long authorId) {
        return (root, query, cb) ->
                cb.equal(root.get("author").get("id"), authorId);
    }

    public static Specification<Post> hasCategory(Category category) {
        return (root, query, cb) ->
                cb.equal(root.get("category"), category);
    }

    public static Specification<Post> titleMatches(String title) {
        // 검색 점수가 0보다 큰 게시글만 조회
        return (root, query, cb) ->
                cb.greaterThan(
                        cb.function(
                                "match_against",
                                Double.class,
                                root.get("title"),
                                cb.literal(title)
                        ),
                        0.0
                );
    }
}
