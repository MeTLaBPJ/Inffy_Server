package org.inffy.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inffy.domain.chat.dto.req.ChatRequestDto;
import org.inffy.domain.chat.service.ChatService;
import org.inffy.domain.fcm.service.FcmService;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService chatService;
    private final FcmService fcmService;

    @MessageMapping("/chat/room/message/{roomId}")
    public void sendMessageChat(Principal principal, @Payload ChatRequestDto req, @DestinationVariable Long roomId){
        sendingOperations.convertAndSend("/sub/chat/room/" + roomId, chatService.convertToChatResponse(principal, req));
        fcmService.sendFcmChatAlarm(chatService.saveMessageChat(principal, req, roomId));
    }

    @MessageMapping("/chat/room/enter/{roomId}")
    public void sendEnterChat(@Header("user") Principal principal, @DestinationVariable Long roomId){
        sendingOperations.convertAndSend("/sub/chat/room/"+roomId, chatService.convertToEnterChatResponse(principal));
    }

    @MessageMapping("/chat/room/leave/{roomId}")
    public void sendLeaveChat(@Header("user") Principal principal, @DestinationVariable Long roomId){
        sendingOperations.convertAndSend("/sub/chat/room/"+roomId, chatService.convertToLeaveChatResponse(principal));
        chatService.handleLeavingMember(principal, roomId);
    }
}