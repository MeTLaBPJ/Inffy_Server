package org.inffy.domain.chatroom.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.inffy.domain.member.dto.res.MemberSummaryResponseDto;

import java.util.List;

@Schema(description = "참여자 목록 Response")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatroomParticipantResponseDto {

    @Schema(description = "남자 참여자 목록")
    List<MemberSummaryResponseDto> maleMemberSummary;

    @Schema(description = "여자 참여자 목록")
    List<MemberSummaryResponseDto> femaleMemberSummary;
}

