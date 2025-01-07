package org.inffy.domain.chatroom.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inffy.domain.chatroom.dto.req.ChatroomCreateRequestDto;
import org.inffy.domain.chatroom.dto.req.ChatroomScheduleRequestDto;
import org.inffy.domain.chatroom.dto.res.*;
import org.inffy.domain.chatroom.service.ChatroomService;
import org.inffy.domain.common.dto.ResponseDto;
import org.inffy.domain.fcm.service.FcmService;
import org.inffy.domain.member.entity.Member;
import org.inffy.global.security.jwt.util.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Chatroom", description = "Chatroom API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat-rooms")
public class ChatroomController {

    // TODO 채팅룸 기간 만료시, 채팅룸 삭제되는 기능

    private final ChatroomService chatroomService;
    private final JwtTokenProvider jwtTokenProvider;


    @Operation(description = "참여중인 채팅룸 조회")
    @GetMapping("/active")
    public ResponseEntity<ResponseDto<List<ChatroomParticipationResponseDto>>> getParticipatingChatroom(@AuthenticationPrincipal Member member){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.findParticipatingChatroom(member), "참여중인 채팅룸 조회 완료"));
    }

    @Operation(description = "모집중인 채팅룸 조회")
    @GetMapping("/recruiting")
    public ResponseEntity<ResponseDto<List<ChatroomRecruitingResponseDto>>> getRecruitingChatroom(){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.findRecruitingChatroom(), "모집중인 채팅룸 조회 완료"));
    }

    @Operation(description = "채팅룸 상세 조회")
    @GetMapping("/{roomId}")
    public ResponseEntity<ResponseDto<ChatroomDetailResponseDto>> getChatroomDetail(@AuthenticationPrincipal Member member,
                                                                                    @PathVariable Long roomId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.findChatroomDetail(member, roomId), "채팅룸 상세조회 완료"));
    }

    @Operation(description = "채팅룸 입장")
    @PostMapping("/{roomId}/participants")
    public ResponseEntity<ResponseDto<Boolean>> participateChatroom(@AuthenticationPrincipal Member member,
                                                                    @PathVariable Long roomId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.participateChatroom(member, roomId), "채팅룸 입장 처리 완료"));
    }

    @Operation(description = "채팅룸 시작")
    @PatchMapping("/{roomId}/status/start")
    public ResponseEntity<ResponseDto<ChatroomStartResponseDto>> startChatroom(@AuthenticationPrincipal Member member,
                                                                               @PathVariable Long roomId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.startChatroom(member, roomId), "채팅룸 시작 처리 완료"));
    }

    @Operation(description = "채팅룸 데드라인 설정")
    @PatchMapping("/{roomId}/status/schedule")
    public ResponseEntity<ResponseDto<Boolean>> scheduleChatroom(@AuthenticationPrincipal Member member,
                                                                 @PathVariable Long roomId,
                                                                 @Valid@RequestBody ChatroomScheduleRequestDto req){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.scheduleChatroom(member, roomId, req), "채팅룸 일정 처리 완료"));
    }

    @Operation(description = "채팅룸 나가기 - 일반 맴버")
    @PatchMapping("/{roomId}/leave")
    public ResponseEntity<ResponseDto<Long>> leaveChatroom(@AuthenticationPrincipal Member member,
                                                           @PathVariable Long roomId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.leaveChatroom(member, roomId), "채팅룸 나가기 처리 완료"));
    }

    @Operation(description = "채팅룸 나가기 - 채팅룸 맴버(뒤로가기,입장하기)")
    @PatchMapping("/{roomId}/back")
    public ResponseEntity<ResponseDto<Boolean>> backChatroom(@AuthenticationPrincipal Member member,
                                                            @PathVariable Long roomId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.updateActiveStatus(member, roomId), "채팅룸 입장 상태 변경 완료"));
    }

    @Operation(description = "채팅룸 나가기 - 호스트 맴버")
    @DeleteMapping("/{roomId}/host")
    public ResponseEntity<ResponseDto<Long>> deleteChatroom(@AuthenticationPrincipal Member member,
                                                            @PathVariable Long roomId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.deleteChatroom(member, roomId), "채팅룸 삭제 처리 완료"));
    }

    @Operation(description = "채팅룸 참여/대기 맴버 정보 조회")
    @GetMapping("/{roomId}/participants")
    public ResponseEntity<ResponseDto<ChatroomParticipantResponseDto>> getChatroomParticipants(@PathVariable Long roomId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.getChatroomParticipants(roomId), "채팅룸 참여/대기 인원 조회 완료"));
    }

    @Operation(description = "채팅룸 생성")
    @PostMapping
    public ResponseEntity<ResponseDto<Long>> createChatroom(@AuthenticationPrincipal Member member,
                                                            @Valid@RequestBody ChatroomCreateRequestDto req){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(chatroomService.createChatroom(member, req), "채팅룸 생성 완료"));
    }
}