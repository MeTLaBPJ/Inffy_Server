package org.inffy.domain.member.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequestDto {

    @NotBlank(message = "이메일이 비어있습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@inu\\.ac\\.kr$", message = "이메일은 @inu.ac.kr 도메인만 허용됩니다.")
    private String schoolEmail;

    @NotBlank(message = "비밀번호가 비어있습니다.")
    private String password;

    public UsernamePasswordAuthenticationToken toAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(schoolEmail, password);
    }
}