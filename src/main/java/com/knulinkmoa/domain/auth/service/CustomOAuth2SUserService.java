package com.knulinkmoa.domain.auth.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2SUserService extends DefaultOAuth2UserService {


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest); // 유저 정보를 담고 있음

        System.out.println("oAuth2User = " + oAuth2User);

        // http://localhost:8080/oauth2/authorization/kakao에서 kakao가 registrationId
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        if (registrationId.equals("naver")) {

        } else if (registrationId.equals("kakao")) {

        } else {
            return null;
        }



    }
}
