package com.knulinkmoa.auth.service;

import com.knulinkmoa.auth.dto.request.OAuth2DTO;
import com.knulinkmoa.domain.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User implements OAuth2User {

    private Member member;
    private Map<String, Object> attributes;

    public CustomOAuth2User(Member member) {
        this.member = member;
    }

    public CustomOAuth2User(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return String.valueOf(member.getRole());
            }
        });

        return authorities;
    }

    @Override
    public String getName() {
        return member.getName();
    }

    public String getEmail() {
        return member.getEmail();
    }
}
