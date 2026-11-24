package org.sopt.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.code.AuthErrorCode;
import org.sopt.domain.auth.domain.entity.SocialAccount;
import org.sopt.domain.auth.dto.response.SocialMemberResponse;
import org.sopt.domain.auth.exception.AuthValidationException;
import org.sopt.domain.auth.repository.SocialAccountRepository;
import org.sopt.domain.member.domain.entity.Member;
import org.sopt.domain.member.exception.MemberWithdrawnException;
import org.sopt.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SocialMemberService {

    private final SocialAccountRepository socialAccountRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long findOrCreateMember(SocialMemberResponse memberInfo) {
        return socialAccountRepository.findByProviderAndProviderId(
                        memberInfo.provider(),
                        memberInfo.providerId()
                )
                .map(account -> {
                    Member member = account.getMember();

                    if (member.isDeleted()) {
                        throw new MemberWithdrawnException();
                    }

                    return member.getId();
                })
                .orElseGet(() -> createMember(memberInfo));
    }

    private Long createMember(SocialMemberResponse memberInfo) {
        if (memberRepository.existsByEmail(memberInfo.email())) {
            throw new AuthValidationException(AuthErrorCode.EMAIL_ALREADY_EXISTS);
        }

        Member member = memberRepository.save(
                Member.createSocialMember(
                        memberInfo.name(),
                        memberInfo.email()
                )
        );

        socialAccountRepository.save(
                new SocialAccount(
                        memberInfo.provider(),
                        memberInfo.providerId(),
                        member
                )
        );

        return member.getId();
    }
}
