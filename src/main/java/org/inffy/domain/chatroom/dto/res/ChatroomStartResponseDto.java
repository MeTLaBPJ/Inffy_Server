package org.inffy.domain.chatroom.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "채팅룸 시작 Response")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatroomStartResponseDto {

    @Schema(description = "채팅룸 아이디", example = "1")
    private Long roomId;

    @Schema(description = "채팅룸 이름", example = "28일에 같이 축구 보실분!")
    private String title;

    @Schema(description = "호스트 닉네임", example = "컴공 횃불이")
    private String hostNickname;
}
