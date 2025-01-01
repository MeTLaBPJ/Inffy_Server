package org.inffy.domain.member.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.inffy.domain.member.enums.Gender;

@Schema(description = "유저 요약 정보 Response")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSummaryResponseDto {

    @Schema(description = "전공", example = "컴퓨터공학부")
    private String department;

    @Schema(description = "학번", example = "202001682")
    private String studentId;

    @Schema(description = "닉네임", example = "컴공횃불이")
    private String nickname;

    @Schema(description = "성별", example = "MALE")
    private Gender gender;
}