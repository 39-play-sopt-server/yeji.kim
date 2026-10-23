package org.sopt.post.repository.specification;

import org.sopt.post.domain.entity.Category;
import org.sopt.post.domain.entity.Post;
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

    public static Specification<Post> titleContains(String title) {
        return (root, query, cb) ->
                cb.like(root.get("title"), "%" + title + "%");
    }
}
