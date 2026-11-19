package org.sopt.domain.auth.dto.response;

import org.sopt.domain.auth.domain.entity.OAuthProvider;

public record SocialMemberInfo(
        OAuthProvider provider,
        String providerId,
        String name,
        String email
) {
}
