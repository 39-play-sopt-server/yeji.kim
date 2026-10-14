package org.sopt.post.repository;

import org.sopt.post.domain.Category;
import org.sopt.post.domain.Post;

import java.util.List;

public interface PostRepository {

    void save(Post post);

    Post findById(Long id);

    List<Post> findAll();

    List<Post> findPage(int offset, int limit, Category category);

    void updatePost(Post post);

    void deletePost(Long id);
}
