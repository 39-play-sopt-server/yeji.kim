package org.sopt.domain.member.dto.response;

import org.sopt.domain.member.domain.entity.Member;
import org.sopt.domain.member.domain.entity.MemberRole;

public record MemberResponse(
        Long id,
        String name,
        String email,
        MemberRole role,
        boolean deleted
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getRole(),
                member.isDeleted()
        );
    }
}
