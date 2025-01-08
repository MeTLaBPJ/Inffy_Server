package org.inffy.domain.member.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.inffy.domain.member.enums.BodyType;
import org.inffy.domain.member.enums.DrinkingHabit;
import org.inffy.domain.member.enums.Religion;
import org.inffy.domain.member.enums.SmokingStatus;

@Getter
public class MemberRequestDto {

    @NotBlank(message = "자기소개는 공백이 될 수 없습니다.")
    private String introduction;

    @NotNull(message = "키는 공백이 될 수 없습니다.")
    private Integer height;

    @NotNull(message = "체형은 공백이 될 수 없습니다.")
    private BodyType bodyType;

    @NotNull(message = "종교는 공백이 될 수 없습니다.")
    private Religion religion;

    @NotNull(message = "음주는 공백이 될 수 없습니다.")
    private DrinkingHabit drinkingHabit;

    @NotNull(message = "흡연은 공백이 될 수 없습니다.")
    private SmokingStatus smokingStatus;
}
