package org.sopt.domain.auth.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.global.entity.BaseTimeEntity;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseTimeEntity {

    @Id
    @Column(length = 36)
    private String tokenId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Instant expiresAt;

    public RefreshToken(
            String tokenId,
            Long memberId,
            Instant expiresAt
    ) {
        this.tokenId = tokenId;
        this.memberId = memberId;
        this.expiresAt = expiresAt;
    }
}
