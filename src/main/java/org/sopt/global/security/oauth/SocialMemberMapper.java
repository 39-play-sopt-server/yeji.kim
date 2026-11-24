package org.sopt.global.security.oauth;

import org.sopt.domain.auth.dto.response.SocialMemberResponse;

import java.util.Map;

public interface SocialMemberMapper {

    boolean supports(String registrationId);

    SocialMemberResponse map(Map<String, Object> attributes);
}
