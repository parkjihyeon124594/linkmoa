package com.knulinkmoa.global.details;

import com.knulinkmoa.domain.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {


    private Member member;
    private Map<String,Object> attributs;


    //일반 로그인
    public PrincipalDetails(Member member){
        this.member=member;
    }

    //Oauth 로그인
    public PrincipalDetails(Member member,Map<String,Object> attributs){
        this.member=member;
        this.attributs=attributs;
    }

    @Override
    public String getName() {
        return member.getName();
    }

    public String getEmail(){
        return member.getEmail();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributs;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities =new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole().name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getName();
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true => 만료되지 않았음.
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true => 만료되지 않았음.
    }

    //패스워드 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true => 만료되지 않았음.
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true => 사용 가능
    }
}
