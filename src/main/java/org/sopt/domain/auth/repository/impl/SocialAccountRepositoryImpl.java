package org.sopt.domain.auth.repository.impl;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.domain.entity.OAuthProvider;
import org.sopt.domain.auth.domain.entity.SocialAccount;
import org.sopt.domain.auth.repository.SocialAccountJpaRepository;
import org.sopt.domain.auth.repository.SocialAccountRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SocialAccountRepositoryImpl implements SocialAccountRepository {

    private final SocialAccountJpaRepository jpaRepository;

    @Override
    public SocialAccount save(SocialAccount socialAccount) {
        return jpaRepository.save(socialAccount);
    }

    @Override
    public Optional<SocialAccount> findByProviderAndProviderId(
            OAuthProvider provider,
            String providerId
    ) {
        return jpaRepository.findByProviderAndProviderId(
                provider,
                providerId
        );
    }
}
