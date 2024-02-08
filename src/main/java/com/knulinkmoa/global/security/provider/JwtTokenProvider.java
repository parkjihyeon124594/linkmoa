package com.knulinkmoa.global.security.provider;

import lombok.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtTokenProvider {

    private final Key key;

    public JwtTokenProvider(@Value) {

    }
}
