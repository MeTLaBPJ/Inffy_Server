package org.inffy.domain.chatroom.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.inffy.domain.member.dto.res.MemberSummaryResponseDto;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "채팅룸 상세 정보 Response")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatroomDetailResponseDto {

    @Schema(description = "채팅방 제목", example = "타임스페이스에서 노실분!")
    private String title;

    @Schema(description = "채팅방 부제목", example = "볼링치고 노래방 가고 싶어요!")
    private LocalDate deadLine;

    @Schema(description = "남자 참여자 목록")
    List<MemberSummaryResponseDto> maleMemberSummary;

    @Schema(description = "여자 참여자 목록")
    List<MemberSummaryResponseDto> femaleMemberSummary;

}