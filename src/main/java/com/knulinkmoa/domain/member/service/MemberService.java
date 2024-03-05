package com.knulinkmoa.domain.member.service;

import com.knulinkmoa.auth.dto.request.OAuth2DTO;
import com.knulinkmoa.domain.member.entity.Member;
import com.knulinkmoa.domain.member.entity.Role;
import com.knulinkmoa.domain.member.exception.MemberErrorCode;
import com.knulinkmoa.domain.member.reposotiry.MemberRepository;
import com.knulinkmoa.global.exception.GlobalException;
import com.knulinkmoa.global.util.OauthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * oAuth2Dto를 바탕으로 member를 saveOrUpdate
     * @param email,name
     * @return
     */
    @Transactional
    public Member save(String name,String email){
        Member member = memberRepository.save(Member.builder()
                        .email(email)
                        .password(passwordEncoder.encode(OauthUtil.oauthPasswordKey))
                        .role(Role.ROLE_USER)
                        .name(name)
                        .build());
        return member;
    }

    public boolean existMemberByEmail(String email){
        return memberRepository.existsByEmail(email);
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

}
