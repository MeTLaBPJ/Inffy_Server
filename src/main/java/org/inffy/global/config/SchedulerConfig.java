package org.inffy.global.config;

import lombok.RequiredArgsConstructor;
import org.inffy.domain.chatroom.service.ChatroomService;
import org.inffy.domain.member.service.MemberService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerConfig {

    private final ChatroomService chatroomService;
    private final MemberService memberService;

    @Scheduled(cron = "0 1 0 * * ?")
    public void deleteExpiredChatroom(){
        chatroomService.deleteExpiredChatroom();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void initializeTicket() {
        memberService.initializeTicket();
    }
}