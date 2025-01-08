package org.inffy.domain.chat.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(description = "일반 채팅 Response")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponseDto {

    @Schema(description = "유저 닉네임", example = "컴공 횃불이")
    private String nickname;

    @Schema(description = "채팅 내용", example = "오 그거 좋은데요?")
    private String content;

    @Schema(description = "전송 시간", example = "12:30")
    private LocalDate timeStamp;
}
