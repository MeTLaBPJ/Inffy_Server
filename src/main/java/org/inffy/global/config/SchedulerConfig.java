package org.inffy.global.config;

import lombok.RequiredArgsConstructor;
import org.inffy.domain.chatroom.service.ChatroomService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerConfig {

    private final ChatroomService chatroomService;

    @Scheduled(cron = "0 1 0 * * ?")
    public void deleteExpiredChatroom(){
        chatroomService.deleteExpiredChatroom();
    }
}