package org.sopt.domain.member.repository;

import jakarta.persistence.LockModeType;
import org.sopt.domain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByIdAndDeletedFalse(Long memberId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Member> findWithLockById(Long memberId);
}
