package org.inffy.domain.chatroom.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Schema(description = "채팅룸 생성 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatroomCreateRequestDto {

    @Schema(description = "채팅방 제목", example = "양꼬치 드시러 가실분 구해요")
    @NotBlank(message = "채팅방 제목은 공백일 수 없습니다.")
    private String title;

    @Schema(description = "채팅방 부제목", example = "타임스페이스 호우 양꼬치!")
    @NotBlank(message = "채팅방 부제목은 공백일 수 없습니다.")
    private String subtitle;

    @Schema(description = "남자 최대 수용 인원", example = "5")
    @NotNull(message = "남자 최대 수용 인원 값이 비어있습니다.")
    @Min(value = 0, message = "인원 수는 0명 이상이어야 합니다.")
    @Max(value = 5, message = "인원 수는 5명 이하이어야 합니다.")
    private Integer maxMaleMembers;

    @Schema(description = "여자 최대 수용 인원", example = "5")
    @NotNull(message = "여자 최대 수용 인원 값이 비어있습니다.")
    @Min(value = 0, message = "인원 수는 0명 이상이어야 합니다.")
    @Max(value = 5, message = "인원 수는 5명 이하이어야 합니다.")
    private Integer maxFemaleMembers;
}
