package org.sopt.domain.post.repository;

import org.sopt.domain.post.domain.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    // 이미 좋아요 눌렀는지 확인
    boolean existsByMemberIdAndPostId(Long memberId, Long postId);

    // 좋아요 취소 하려고 기록 찾음
    Optional<PostLike> findByMemberIdAndPostId(Long memberId, Long postId);

    // 좋아요 개수 조회
    long countByPostId(Long postId);

    void deleteByPostId(Long postId);
}
