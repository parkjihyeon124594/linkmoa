package com.knulinkmoa.domain.member.entity;

import com.knulinkmoa.auth.dto.OAuth2Response;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String username; // 사용자 아이디

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role; // 사용자의 역할

    @Column(name = "nickname")
    private String nickname; // 사용자 닉네임

    @Builder
    public Member(Long id, String username, String email, Role role, String nickname) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.nickname = nickname;
    }

    public void update(OAuth2Response oAuth2Response) {
        this.email = oAuth2Response.getEmail();
        this.nickname = oAuth2Response.getName();
    }
}
