package org.sopt.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.domain.member.dto.request.CreateMemberRequest;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "MEMBER", description = "회원 API")
public interface MemberControllerDocs {

    @Operation(
            summary = "회원 생성",
            description = "이름을 입력받아 회원을 생성하고 생성된 회원 ID를 반환합니다."
    )
    ResponseEntity<ApiResponse<Long>> createMember(
            @RequestBody CreateMemberRequest request
    );

    @Operation(
            summary = "회원 탈퇴",
            description = "현재 인증된 회원을 탈퇴 처리합니다."
    )
    ResponseEntity<ApiResponse<Void>> deleteMember(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long memberId
    );

    @Operation(
            summary = "내 회원 ID 조회",
            description = "현재 인증된 회원의 ID를 반환합니다."
    )
    ResponseEntity<Long> me(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long memberId
    );
}
