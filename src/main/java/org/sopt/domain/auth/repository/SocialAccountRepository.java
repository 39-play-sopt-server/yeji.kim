package org.sopt.domain.auth.repository;

import org.sopt.domain.auth.domain.entity.OAuthProvider;
import org.sopt.domain.auth.domain.entity.SocialAccount;

import java.util.Optional;

public interface SocialAccountRepository {

    SocialAccount save(SocialAccount socialAccount);

    Optional<SocialAccount> findByProviderAndProviderId(
            OAuthProvider provider,
            String providerId
    );
}
