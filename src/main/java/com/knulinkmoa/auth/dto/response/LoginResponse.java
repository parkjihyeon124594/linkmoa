package com.knulinkmoa.auth.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LoginResponse(
       // String token
        String userinfo
) {
}
