package com.knulinkmoa.domain.member.entity;

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

    @Column(name = "password")
    private String password; // 사용자 패스워드

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role; // 사용자의 역할

    @Column(name = "nickname")
    private String nickname; // 사용자 닉네임

    @Column(name = "phone_number")
    private String phoneNumber; // 사용자 전화번호

    @Builder
    public Member(Long id, String username, String password, Role role, String nickname, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }
}
