package org.sopt.domain.member.controller;

import jakarta.validation.Valid;
import org.sopt.domain.member.code.MemberSuccessCode;
import org.sopt.domain.member.dto.request.CreateMemberRequest;
import org.sopt.domain.member.service.MemberService;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/members")
public class MemberController implements MemberControllerDocs {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createMember(
            @Valid @RequestBody CreateMemberRequest request
    ) {
        Long memberId = memberService.createMember(request);
        return ApiResponse.toResponseEntity(MemberSuccessCode.MEMBER_CREATED, memberId);
    }

    @Override
    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Void>> deleteMember(
            @PathVariable(name = "memberId") Long memberId
    ) {
        memberService.deleteMember(memberId);
        return ApiResponse.toResponseEntity(MemberSuccessCode.MEMBER_DELETED, null);
    }
}
