package com.knulinkmoa.global.details;

import com.knulinkmoa.auth.dto.request.OAuth2DTO;
import com.knulinkmoa.auth.dto.response.GoogleResponse;
import com.knulinkmoa.auth.dto.response.NaverResponse;
import com.knulinkmoa.auth.dto.response.OAuth2Response;
import com.knulinkmoa.domain.member.entity.Member;
import com.knulinkmoa.domain.member.reposotiry.MemberRepository;
import com.knulinkmoa.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService, OAuth2UserService {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        PrincipalDetails principalDetails = new PrincipalDetails(memberService.findMemberByEmail(email));

        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다."));

        return principalDetails;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("principalDetailsService loadUser test : ");
        DefaultOAuth2UserService defaultOAuth2UserService= new DefaultOAuth2UserService();
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);
        Member member=null;

        // 어떤 소셜인지 .. google, naver, kakao
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // DTO에 맞는 response를 가져오고
        OAuth2Response oAuth2Response = getOAuth2Response(registrationId, oAuth2User);

        // oAuth2DTO
        OAuth2DTO oAuth2DTO = createOAuth2DTO(oAuth2User, oAuth2Response);

        // oAuth2DTO를 통해서, 우리 서비스에서 관리할 member를 saveOrUpdate

        if(!memberService.existMemberByEmail(oAuth2DTO.email())){
            member = memberService.save(oAuth2DTO.email(),oAuth2DTO.name());
            System.out.println("save member ");
        }
        else{
            member=memberService.findMemberByEmail(oAuth2DTO.email());
            System.out.println("find member");
        }

        PrincipalDetails principalDetails =new PrincipalDetails(member);
        return principalDetails;
    }

    /**
     *
     * @param registrationId google, naver, kakao 등등
     * @param oAuth2User resource server로 부터 받아온 user 정보
     * @return google, naver, kakao 등에 맞는 oAuth2Response
     */
    private static OAuth2Response getOAuth2Response(String registrationId, OAuth2User oAuth2User) {
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        return oAuth2Response;
    }

    /**
     * OAuth2DTo 생성
     *
     * @param oAuth2User
     * @param oAuth2Response
     * @return
     */
    private static OAuth2DTO createOAuth2DTO(OAuth2User oAuth2User, OAuth2Response oAuth2Response) {
        return OAuth2DTO.builder()
                .attributes(oAuth2User.getAttributes())
                .name(oAuth2Response.getName())
                .email(oAuth2Response.getEmail())
                .role("ROLE_USER")
                .build();
    }
}
