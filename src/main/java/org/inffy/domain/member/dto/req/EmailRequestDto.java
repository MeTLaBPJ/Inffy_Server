package org.inffy.domain.member.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class EmailRequestDto {

    @Email
    @NotEmpty(message = "이메일이 비어있습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@inu\\.ac\\.kr$", message = "이메일은 @inu.ac.kr 도메인만 허용됩니다.")
    private String schoolEmail;
}
