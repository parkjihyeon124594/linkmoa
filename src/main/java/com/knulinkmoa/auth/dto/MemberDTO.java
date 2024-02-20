package com.knulinkmoa.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {

    private String role;
    private String email;
    private String nickname;
    private String username;

    @Builder
    public MemberDTO(String role, String email, String nickname, String username) {
        this.role = role;
        this.email = email;
        this.nickname = nickname;
        this.username = username;
    }
}
