package org.sopt.domain.post.repository;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.sopt.domain.post.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@NullMarked
@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    @Override
    @EntityGraph(attributePaths = "author")
    Page<Post> findAll(@Nullable Specification<Post> spec, Pageable pageable);
}
