package com.knulinkmoa.auth.service;

import com.knulinkmoa.auth.dto.request.OAuth2DTO;
import com.knulinkmoa.auth.dto.response.GoogleResponse;
import com.knulinkmoa.auth.dto.response.NaverResponse;
import com.knulinkmoa.auth.dto.response.OAuth2Response;
import com.knulinkmoa.domain.member.entity.Member;
import com.knulinkmoa.domain.member.entity.Role;
import com.knulinkmoa.domain.member.reposotiry.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // user 정보를 가져옴
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 어떤 소셜인지 .. google, naver, kakao
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // DTO에 맞는 response를 가져오고
        OAuth2Response oAuth2Response = getOAuth2Response(registrationId, oAuth2User);

        // oAuth2DTO
        OAuth2DTO oAuth2DTO = createOAuth2DTO(oAuth2User, oAuth2Response);

        // oAuth2DTO를 통해서, 우리 서비스에서 관리할 member를 saveOrUpdate
        Member member = saveOrUpdate(oAuth2DTO);

        // customOAuth2User를 리턴
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(oAuth2DTO);
        return customOAuth2User;
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
                .name(oAuth2Response.getEmail())
                .email(oAuth2Response.getName())
                .role("ROLE_USER")
                .build();
    }

    /**
     * oAuth2Dto를 바탕으로 member를 saveOrUpdate
     * @param oAuth2DTO
     * @return
     */
    private Member saveOrUpdate(OAuth2DTO oAuth2DTO) {
        Member member = memberRepository.findByEmail(oAuth2DTO.email())
                .map(existingMember -> { // 만약 member가 있으면
                    existingMember.update(oAuth2DTO); // update
                    return existingMember;
                })
                .orElseGet(() -> { // 만약 member가 없으면
                    return oAuth2DTO.oAuth2DtoToMember(oAuth2DTO);
                });

        return memberRepository.save(member);
    }
}
