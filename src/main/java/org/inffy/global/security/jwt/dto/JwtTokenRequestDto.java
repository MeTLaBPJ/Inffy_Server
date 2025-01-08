package org.inffy.global.security.jwt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtTokenRequestDto {

    private String accessToken;
    private String refreshToken;
}
