package org.sopt.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateMemberRequest (
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password
) {
}
