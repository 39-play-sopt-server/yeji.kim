package org.sopt.post.repository;

import org.sopt.post.domain.entity.Category;
import org.sopt.post.domain.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    List<Post> findByCategory(Category category, Pageable pageable);

    List<Post> findByTitleContaining(String title, Pageable pageable);

    List<Post> findByTitleContainingAndCategory(String title, Category category, Pageable pageable);
}
