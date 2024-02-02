package com.knulinkmoa.domain.member.service;

import com.knulinkmoa.domain.member.dto.request.MemberSaveRequest;
import com.knulinkmoa.domain.member.entity.Member;
import com.knulinkmoa.domain.member.reposotiry.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long saveMember(MemberSaveRequest request) {

        Member member = Member.builder()
                .email(request.email())
                .password(request.password())
                .build();

        memberRepository.save(member);

        return member.getId();
    }

}
