package org.sopt.global.security.oauth;

import org.sopt.domain.auth.domain.entity.OAuthProvider;
import org.sopt.domain.auth.dto.response.SocialMemberResponse;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

@Component
public class GoogleMemberMapper implements SocialMemberMapper {

    @Override
    public boolean supports(String registrationId) {
        return "google".equals(registrationId);
    }

    @Override
    public SocialMemberResponse map(Map<String, Object> attributes) {
        String providerId = (String) attributes.get("sub");
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        if (!StringUtils.hasText(providerId)
                || !StringUtils.hasText(email)
                || !Boolean.TRUE.equals(attributes.get("email_verified"))) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("invalid_user_info")
            );
        }

        return new SocialMemberResponse(
                OAuthProvider.GOOGLE,
                providerId,
                StringUtils.hasText(name) ? name : "구글 사용자",
                email
        );
    }
}
