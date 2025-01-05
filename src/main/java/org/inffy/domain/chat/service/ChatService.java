package org.inffy.domain.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inffy.domain.chat.dto.req.ChatRequestDto;
import org.inffy.domain.chat.dto.res.ChatResponseDto;
import org.inffy.domain.chat.entity.Chat;
import org.inffy.domain.chat.enums.ChatType;
import org.inffy.domain.chat.repository.ChatRepository;
import org.inffy.domain.chatroom.entity.Chatroom;
import org.inffy.domain.chatroom.repository.ChatroomRepository;
import org.inffy.domain.chatroom.service.ChatroomService;
import org.inffy.domain.member.entity.Member;
import org.inffy.domain.member.repository.MemberRepository;
import org.inffy.global.exception.entity.RestApiException;
import org.inffy.global.exception.error.CustomErrorCode;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatroomService chatroomService;

    private final ChatRepository chatRepository;
    private final ChatroomRepository chatroomRepository;
    private final MemberRepository memberRepository;

    public ChatResponseDto convertToChatResponse(Principal principal, ChatRequestDto req){
        Member member = memberRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.MEMBER_NOT_FOUND));

        return ChatResponseDto.builder()
                .nickname(member.getNickname())
                .content(req.getContent())
                .timeStamp(LocalDate.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

    public ChatResponseDto convertToEnterChatResponse(Principal principal){
        Member member = memberRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.MEMBER_NOT_FOUND));

        return ChatResponseDto.builder()
                .nickname(member.getNickname())
                .content("이/가 입장하셨습니다.")
                .timeStamp(LocalDate.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

    public ChatResponseDto convertToLeaveChatResponse(Principal principal){
        Member member = memberRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.MEMBER_NOT_FOUND));

        return ChatResponseDto.builder()
                .nickname(member.getNickname())
                .content("이/가 퇴장하셨습니다.")
                .timeStamp(LocalDate.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

    public void saveMessageChat(Principal principal, ChatRequestDto req, Long roomId){
        Member member = memberRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.MEMBER_NOT_FOUND));

        Chatroom chatroom = chatroomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.CHATROOM_NOT_FOUND));

        Chat chat = Chat.builder()
                .senderNickname(member.getNickname())
                .content(req.getContent())
                .type(ChatType.CHAT)
                .chatroom(chatroom)
                .build();

        chatroom.getChats().add(chat);
        chatRepository.save(chat);
    }

    public void handleLeavingMember(Principal principal, Long roomId){
        Member member = memberRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.MEMBER_NOT_FOUND));

        chatroomService.leaveChatroom(member, roomId);
    }
}