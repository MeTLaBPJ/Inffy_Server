package org.inffy.domain.member.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupResponseDto {

    private String schoolEmail;
    private String username;

    @Builder
    public SignupResponseDto(String schoolEmail, String username) {
        this.schoolEmail = schoolEmail;
        this.username = username;
    }
}
