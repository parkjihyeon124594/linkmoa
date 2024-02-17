package com.knulinkmoa.global.security.details;

import com.knulinkmoa.domain.member.entity.Member;
import com.knulinkmoa.domain.member.reposotiry.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member memberData = memberRepository.findByUsername(username);

        if (memberData != null) {
            return new CustomUserDetails(memberData);
        }

        return null;
    }
}
