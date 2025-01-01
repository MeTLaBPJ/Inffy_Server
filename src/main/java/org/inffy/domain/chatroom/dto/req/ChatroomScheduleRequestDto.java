package org.inffy.domain.chatroom.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Schema(description = "채팅룸 데드라인 설정 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatroomScheduleRequestDto {

    @Schema(description = "설정 날짜", example = "2025-01-28")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadLine;
}
