package org.inffy.domain.member.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.inffy.domain.member.enums.BodyType;
import org.inffy.domain.member.enums.DrinkingHabit;
import org.inffy.domain.member.enums.Religion;
import org.inffy.domain.member.enums.SmokingStatus;
import org.inffy.global.annotation.EnumPattern;

@Getter
public class MemberRequestDto {

    @NotBlank(message = "자기소개는 공백이 될 수 없습니다.")
    private String introduction;

    @NotNull(message = "키는 공백이 될 수 없습니다.")
    private Integer height;

    @NotNull(message = "체형은 공백이 될 수 없습니다.")
    @EnumPattern(enumClass = BodyType.class,  message = "유효하지 않은 체형입니다.", ignoreCase = true)
    private String bodyType;

    @NotNull(message = "종교는 공백이 될 수 없습니다.")
    @EnumPattern(enumClass = Religion.class, message = "유효하지 않은 종교입니다.", ignoreCase = true)
    private String religion;

    @NotNull(message = "음주 습관은 공백이 될 수 없습니다.")
    @EnumPattern(enumClass = DrinkingHabit.class, message = "유효하지 않은 음주 습관입니다.", ignoreCase = true)
    private String drinkingHabit;

    @NotNull(message = "흡연 습관은 공백이 될 수 없습니다.")
    @EnumPattern(enumClass = SmokingStatus.class, message = "유효하지 않은 흡연 습관입니다.", ignoreCase = true)
    private String smokingStatus;
}
