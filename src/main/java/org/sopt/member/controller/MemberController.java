package org.sopt.member.controller;

import jakarta.validation.Valid;
import org.sopt.global.response.ApiResponse;
import org.sopt.member.code.MemberSuccessCode;
import org.sopt.member.dto.request.CreateMemberRequest;
import org.sopt.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createMember(
            @Valid @RequestBody CreateMemberRequest request
    ) {
        Long memberId = memberService.createMember(request);
        return ApiResponse.toResponseEntity(MemberSuccessCode.MEMBER_CREATED, memberId);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Void>> deleteMember(
            @PathVariable(name = "memberId") Long memberId
    ) {
        memberService.deleteMember(memberId);
        return ApiResponse.toResponseEntity(MemberSuccessCode.MEMBER_DELETED, null);
    }
}
