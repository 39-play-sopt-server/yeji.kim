package org.sopt.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.domain.member.dto.request.CreateMemberRequest;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
            description = "회원 ID에 해당하는 회원을 탈퇴 처리합니다. 회원 데이터는 삭제하지 않고 탈퇴 상태로 변경합니다."
    )
    ResponseEntity<ApiResponse<Void>> deleteMember(
            @PathVariable(name = "memberId") Long memberId
    );
}