package org.inffy.domain.fcm.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "FCM 토큰 등록 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FcmRequestDto {

    @Schema(description = "학교 이메일", example = "ahh0520@inu.ac.kr")
    @NotBlank(message = "학교 이메일은 공백이 될 수 없습니다.")
    private String schoolEmail;

    @Schema(description = "FCM 인증 토큰", example = "cNj:APdOW3_z9vCnYQX6xJ1FhvvP9_qLZWuQrmd4Zp")
    @NotBlank(message = "FCM 인증 토큰은 공백이 될 수 없습니다.")
    private String fcmToken;
}
