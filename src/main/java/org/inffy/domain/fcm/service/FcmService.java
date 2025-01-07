package org.inffy.domain.fcm.service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inffy.domain.chat.entity.Chat;
import org.inffy.domain.chatroom.entity.Chatroom;
import org.inffy.domain.chatroom.repository.ChatroomRepository;
import org.inffy.domain.fcm.dto.req.FcmRequestDto;
import org.inffy.domain.member.entity.Member;
import org.inffy.domain.member.repository.MemberRepository;
import org.inffy.global.exception.entity.RestApiException;
import org.inffy.global.exception.error.CustomErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

    private final MemberRepository memberRepository;
    private final ChatroomRepository chatroomRepository;
    private final FirebaseMessaging firebaseMessaging;

    public boolean saveFcmToken(FcmRequestDto req){
        Member member = memberRepository.findBySchoolEmail(req.getSchoolEmail())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.MEMBER_NOT_FOUND));

        member.updateFcmToken(req.getFcmToken());

        return true;
    }

    public void sendFcmChatAlarm(Chat chat){

        Chatroom chatroom = chat.getChatroom();

        List<String> registrationTokens = chatroomRepository.findFcmTokensByChatroomId(chatroom.getId());

        MulticastMessage message = MulticastMessage.builder()
                .putData("chatroomTitle", chatroom.getTitle())
                .putData("userNickname", chat.getSenderNickname())
                .putData("content", chat.getContent())
                .addAllTokens(registrationTokens)
                .build();

        try{
            BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
        }catch (FirebaseMessagingException e){
            log.info("FCM Exception occurred with error msg : {}", e.getMessagingErrorCode());
            throw new RestApiException(CustomErrorCode.FCM_MESSAGING_FAILED);
        }
    }

    public void sendFcmChatroomAlarm(Chatroom chatroom){

        List<String> registrationTokens = chatroomRepository.findFcmTokensByChatroomId(chatroom.getId());

        MulticastMessage message = MulticastMessage.builder()
                .putData("chatroomTitle", chatroom.getTitle())
                .putData("content", "이/가 시작되었습니다! 대화를 나눠보세요")
                .addAllTokens(registrationTokens)
                .build();

        try{
            BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
        }catch (FirebaseMessagingException e){
            log.info("FCM Exception occurred with error msg : {}", e.getMessagingErrorCode());
            throw new RestApiException(CustomErrorCode.FCM_MESSAGING_FAILED);
        }
    }
}