package com.knulinkmoa.auth.service;

import com.knulinkmoa.auth.dto.CustomOAuth2User;
import com.knulinkmoa.auth.dto.GoogleResponse;
import com.knulinkmoa.auth.dto.MemberDTO;
import com.knulinkmoa.auth.dto.NaverResponse;
import com.knulinkmoa.auth.dto.OAuth2Response;
import com.knulinkmoa.domain.member.entity.Member;
import com.knulinkmoa.domain.member.entity.Role;
import com.knulinkmoa.domain.member.reposotiry.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("oAuth2User = " + oAuth2User);

        // registrationId는 네이버일 경우 naver, 구글일 경우 google
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        // oAuth에서 제공받은 유저정보를 기반으로 만든 사용자마다 고유한 username
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getName();
        Member findMember = memberRepository.findByUsername(username);

        if (findMember == null) {
            Member member = Member.builder()
                    .username(username) // 자체 db에서 관리하는 고유한 이름
                    .email(oAuth2Response.getEmail()) // 멤버의 로그인 시 입력한 아이디 or OAuth2에서 제공한 email
                    .role(Role.valueOf("ROLE_USER"))
                    .nickname(oAuth2Response.getName()) // 닉네임
                    .build();

            memberRepository.save(member);

            MemberDTO memberDTO = MemberDTO.builder()
                    .username(username)
                    .email(oAuth2Response.getEmail())
                    .role("ROLE_USER")
                    .nickname(oAuth2Response.getName())
                    .build();

            return new CustomOAuth2User(memberDTO);
        } else {
            findMember.update(oAuth2Response);

            memberRepository.save(findMember);

            MemberDTO memberDTO = MemberDTO.builder()
                    .username(findMember.getUsername())
                    .email(findMember.getEmail())
                    .nickname(findMember.getNickname())
                    .build();

            return new CustomOAuth2User(memberDTO);
        }
    }
}
