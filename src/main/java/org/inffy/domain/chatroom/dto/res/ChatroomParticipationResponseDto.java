package org.inffy.domain.chatroom.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "참여중인 채팅룸 정보 Response")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatroomParticipationResponseDto {

    @Schema(description = "채팅룸 아이디", example = "1")
    private Long roomId;

    @Schema(description = "채팅방 제목", example = "타임스페이스에서 노실분!")
    private String title;

    @Schema(description = "채팅방 부제목", example = "볼링치고 노래방 가고 싶어요!")
    private String subtitle;

    @Schema(description = "최대 남자 인원 수", example = "3")
    private Integer maxMaleMembers;

    @Schema(description = "최대 여자 인원 수", example = "3")
    private Integer maxFemaleMembers;

    @Schema(description = "사용자의 호스트 여부", example = "true")
    private Boolean isHost;
}
