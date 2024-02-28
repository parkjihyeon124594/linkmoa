package com.knulinkmoa.auth.service;

import com.knulinkmoa.auth.dto.request.OAuth2DTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final OAuth2DTO oAuth2DTO;

    public CustomOAuth2User(OAuth2DTO oAuth2DTO) {
        this.oAuth2DTO = oAuth2DTO;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2DTO.attributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return oAuth2DTO.role();
            }
        });

        return authorities;
    }

    @Override
    public String getName() {
        return oAuth2DTO.name();
    }

    public String getEmail() {
        return oAuth2DTO.email();
    }
}
