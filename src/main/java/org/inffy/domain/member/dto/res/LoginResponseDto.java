package org.inffy.domain.member.dto.res;

import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpires;
    private Date accessTokenExpiresDate;
}
