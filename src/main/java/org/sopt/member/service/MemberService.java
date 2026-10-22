package org.sopt.member.service;

import org.sopt.member.domain.entity.Member;
import org.sopt.member.dto.request.CreateMemberRequest;
import org.sopt.member.exception.MemberNotFoundException;
import org.sopt.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long createMember(CreateMemberRequest request) {
        Member member = new Member(request.name());

        memberRepository.save(member);

        return member.getId();
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        memberRepository.delete(member);
    }
}
