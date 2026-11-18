package org.sopt.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.repository.RefreshTokenRepository;
import org.sopt.domain.auth.service.AuthService;
import org.sopt.domain.member.domain.entity.Member;
import org.sopt.domain.member.dto.request.CreateMemberRequest;
import org.sopt.domain.member.exception.MemberNotFoundException;
import org.sopt.domain.member.exception.MemberWithdrawnException;
import org.sopt.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthService authService;

    public Long createMember(CreateMemberRequest request) {
        return authService.signup(
                request.name(),
                request.email(),
                request.password()
        );
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        if (member.isDeleted()) {
            throw new MemberWithdrawnException();
        }

        member.withdraw();
        refreshTokenRepository.deleteByMemberId(memberId);
    }
}
