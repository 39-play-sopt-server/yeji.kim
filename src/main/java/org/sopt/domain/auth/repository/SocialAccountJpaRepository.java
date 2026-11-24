package org.sopt.domain.auth.repository;

import org.sopt.domain.auth.domain.entity.OAuthProvider;
import org.sopt.domain.auth.domain.entity.SocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialAccountJpaRepository extends JpaRepository<SocialAccount, Long> {

    Optional<SocialAccount> findByProviderAndProviderId(
            OAuthProvider provider,
            String providerId
    );
}
