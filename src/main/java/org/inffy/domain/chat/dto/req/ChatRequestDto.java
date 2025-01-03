package org.inffy.domain.chat.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "일반 채팅 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRequestDto {

    @Schema(description = "채팅 내용", example = "안녕하세요! 다들 반갑습니다!")
    @NotBlank(message = "채팅은 공백일 수 없습니다.")
    private String content;

}
