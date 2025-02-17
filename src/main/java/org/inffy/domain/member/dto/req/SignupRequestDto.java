package org.inffy.domain.member.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.inffy.domain.member.enums.Gender;
import org.inffy.domain.member.enums.Mbti;
import org.inffy.global.annotation.EnumPattern;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupRequestDto {

    @NotBlank(message = "이름이 비어있습니다.")
    private String username;

    @NotBlank(message = "닉네임이 비어있습니다.")
    private String nickname;

    @NotBlank(message = "이메일이 비어있습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@inu\\.ac\\.kr$", message = "이메일은 @inu.ac.kr 도메인만 허용됩니다.")
    private String schoolEmail;

    @NotBlank(message = "비밀번호가 비어있습니다.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "비밀번호는 숫자, 대문자, 소문자, 특수 문자를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인이 비어있습니다.")
    private String passwordCheck;

    @NotNull(message = "성별이 비어있습니다.")
    @EnumPattern(enumClass = Gender.class,  message = "유효하지 않은 성별입니다.", ignoreCase = true)
    private String gender;

    @NotBlank(message = "학번이 비어있습니다.")
    private String studentId;

    @NotBlank(message = "단과 대학이 비어있습니다.")
    private String college;

    @NotBlank(message = "학과가 비어있습니다.")
    private String department;

    @NotNull(message = "생일이 비어있습니다.")
    @Past(message = "생일은 과거 날짜여야 합니다.")
    private LocalDateTime birthday;

    @NotNull(message = "MBTI가 비어있습니다.")
    @EnumPattern(enumClass = Mbti.class,  message = "유효하지 않은 MBTI입니다.", ignoreCase = true)
    private String mbti;

    @NotBlank(message = "Fcm Token이 비어있습니다.")
    private String fcmToken;
}
