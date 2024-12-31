package org.inffy.domain.chatroom.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.inffy.domain.chatroom.service.ChatroomService;
import org.inffy.domain.common.dto.ResponseDto;
import org.inffy.domain.member.entity.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Chatroom", description = "Chatroom API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat-rooms")
public class ChatroomController {

    /**
     * FEAT 1) GET /api/v1/chat-rooms/active                      # 참여중인 채팅룸 조회
     * FEAT 2) GET /api/v1/chat-rooms/recruiting                  # 모집중인 채팅룸 조회
     * FEAT 3) GET /api/v1/chat-rooms/{roomId}                    # 입장한 채팅룸 조회
     * FEAT 4) POST /api/v1/chat-rooms/{roomId}/participants      # 채팅룸 참여
     * FEAT 5) PATCH /api/v1/chat-rooms/{roomId}/status/start     # 채팅룸 시작하기
     * FEAT 6) PATCH /api/v1/chat-rooms/{roomId}/status/schedule  # 채팅룸 약속 잡기
     * FEAT 7) PATCH /api/v1/chat-rooms/{roomId}/leave            # (일반유저) 채팅룸 나가기
     * FEAT 8) DELETE /api/v1/chat-rooms/{roomId}/host            # (호스트) 채팅룸 나가기
     * FEAT 9) GET /api/v1/chat-rooms/{roomId}/participants       # 참여/대기 중인 맴버 조회하기
     * FEAT 10) POST /api/v1/chat-rooms                           # 채팅룸 생성하기
     */

    private final ChatroomService chatroomService;

    @GetMapping("/active")
    public ResponseEntity<ResponseDto<List<ChatroomParticipationResponseDto>>> getParticipatingChatroom(@AuthenticationPrincipal Member member){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.findParticipatingChatroom(member), "참여중인 채팅룸 조회 완료"));
    }

    @GetMapping("/recruiting")
    public ResponseEntity<ResponseDto<List<ChatroomRecruitingResponseDto>>> getRecruitingChatroom(){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.findRecruitingChatroom(), "모집중인 채팅룸 조회 완료"));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<ResponseDto<ChatroomDetailResponseDto>> getChatroomDetail(@AuthenticationPrincipal Member member,
                                                                                    @PathVariable Long roomId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.findChatroomDetail(roomId), "채팅룸 상세조회 완료"));
    }

    @PostMapping("/{roomId}/participants")
    public ResponseEntity<ResponseDto<Boolean>> participateChatroom(@AuthenticationPrincipal Member member,
                                                                                            @PathVariable Long roomId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.participateChatroom(member, roomId), "채팅룸 입장 처리 완료"));
    }

    @PatchMapping("/{roomId}/status/start")
    public ResponseEntity<ResponseDto<ChatroomStartResponseDto>> startChatroom(@AuthenticationPrincipal Member member,
                                                                               @PathVariable Long roomId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.startChatroom(member, roomId), "채팅룸 시작 처리 완료"));
    }

    @PatchMapping("/{roomId}/status/schedule")
    public ResponseEntity<ResponseDto<ChatroomSceduleResponseDto>> scheduleChatroom(@AuthenticationPrincipal Member member,
                                                                                    @PathVariable Long roomId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.scheduleChatroom(member, roomId), "채팅룸 일정 처리 완료"));
    }

    @PatchMapping("/{roomId}/leave")
    public ResponseEntity<ResponseDto<ChatroomLeaveResponseDto>> leaveChatroom(@AuthenticationPrincipal Member member,
                                                                               @PathVariable Long roomId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.leaveChatroom(member, roomId), "채팅룸 나가기 처리 완료"));
    }

    @DeleteMapping("/{roomId}/host")
    public ResponseEntity<ResponseDto<ChatroomDeleteResponseDto>> deleteChatroom(@AuthenticationPrincipal Member member,
                                                                                 @PathVariable Long roomId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.deleteChatroom(member, roomId), "채팅룸 삭제 처리 완료"));
    }

    @GetMapping("/{roomId}/participants")
    public ResponseEntity<ResponseDto<List<ChatroomParticipantResponseDto>>> getChatroomParticipants(@PathVariable Long roomId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(chatroomService.getChatroomParticipants(roomId), "채팅룸 참여/대기 인원 조회 완료"));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<ChatroomCreateResponseDto>> createChatroom(@AuthenticationPrincipal Member member,
                                                                                 @RequestBody ChatroomCreateRequestDto req){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(chatroomService.createChatroom(member, req), "채팅룸 생성 완료"));
    }
}