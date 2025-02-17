package org.inffy.domain.chatroom.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.inffy.domain.chatroom.dto.req.ChatroomCreateRequestDto;
import org.inffy.domain.chatroom.dto.req.ChatroomScheduleRequestDto;
import org.inffy.domain.chatroom.dto.res.*;
import org.inffy.domain.common.dto.ResponseDto;
import org.inffy.domain.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ChatroomApiSpecification {

    @Operation(summary = "참여중인 채팅룸 조회",description = "참여중인 채팅룸 조회")
    @GetMapping
    ResponseEntity<ResponseDto<List<ChatroomParticipationResponseDto>>> getParticipatingChatroom(@AuthenticationPrincipal Member member);

    @Operation(summary = "모집중인 채팅룸 조회",description = "모집중인 채팅룸 조회")
    @GetMapping
    ResponseEntity<ResponseDto<List<ChatroomRecruitingResponseDto>>> getRecruitingChatroom();

    @Operation(summary = "채팅룸 상세 조회",description = "채팅룸 상세 조회")
    @GetMapping
    ResponseEntity<ResponseDto<ChatroomDetailResponseDto>> getChatroomDetail(@AuthenticationPrincipal Member member, @PathVariable Long roomId);

    @Operation(summary = "채팅룸 입장",description = "채팅룸 입장")
    @PostMapping
    ResponseEntity<ResponseDto<Boolean>> participateChatroom(@AuthenticationPrincipal Member member, @PathVariable Long roomId);

    @Operation(summary = "채팅룸 시작",description = "채팅룸 시작")
    @PatchMapping()
    ResponseEntity<ResponseDto<ChatroomStartResponseDto>> startChatroom(@AuthenticationPrincipal Member member, @PathVariable Long roomId);

    @Operation(summary = "채팅룸 데드라인 설정",description = "채팅룸 데드라인 설정")
    @PatchMapping()
    ResponseEntity<ResponseDto<Boolean>> scheduleChatroom(@AuthenticationPrincipal Member member,
                                                          @PathVariable Long roomId,
                                                          @Valid @RequestBody ChatroomScheduleRequestDto req);

    @Operation(summary = "채팅룸 나가기 - 일반 맴버",description = "채팅룸 나가기 - 일반 맴버")
    @PatchMapping()
    ResponseEntity<ResponseDto<Long>> leaveChatroom(@AuthenticationPrincipal Member member,
                                                    @PathVariable Long roomId);

    @Operation(summary = "채팅룸 나가기 - 채팅룸 맴버(뒤로가기,입장하기)",description = "채팅룸 나가기 - 채팅룸 맴버(뒤로가기,입장하기)")
    @PatchMapping()
    ResponseEntity<ResponseDto<Boolean>> backChatroom(@AuthenticationPrincipal Member member,
                                                      @PathVariable Long roomId);

    @Operation(summary = "채팅룸 나가기 - 호스트 맴버",description = "채팅룸 나가기 - 호스트 맴버")
    @DeleteMapping()
    ResponseEntity<ResponseDto<Long>> deleteChatroom(@AuthenticationPrincipal Member member,
                                                     @PathVariable Long roomId);

    @Operation(summary = "채팅룸 참여/대기 맴버 정보 조회",description = "채팅룸 참여/대기 맴버 정보 조회")
    @GetMapping()
    ResponseEntity<ResponseDto<ChatroomParticipantResponseDto>> getChatroomParticipants(@PathVariable Long roomId);

    @Operation(summary = "채팅룸 생성",description = "채팅룸 생성")
    @PostMapping
    ResponseEntity<ResponseDto<Long>> createChatroom(@AuthenticationPrincipal Member member,
                                                     @Valid@RequestBody ChatroomCreateRequestDto req);
}
