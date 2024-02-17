package com.knulinkmoa.domain.member.service;

import com.knulinkmoa.domain.member.dto.request.MemberSignUpDTO;
import com.knulinkmoa.domain.member.entity.Member;
import com.knulinkmoa.domain.member.entity.Role;
import com.knulinkmoa.domain.member.reposotiry.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long saveMember(MemberSignUpDTO request) {

        // 존재하는 닉네임이나 username에 대한 예외처리

        Member member = Member.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ROLE_ADMIN)
                .nickname(request.nickname())
                .phoneNumber(request.phoneNumber())
                .build();

        memberRepository.save(member);

        return member.getId();
    }
}
