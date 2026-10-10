package org.sopt.post.repository;

import org.sopt.post.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository {

    void save(Post post);

    Post findById(Long id);

    List<Post> findAll();

    void updatePost(Post post);

    void deletePost(Long id);
}
