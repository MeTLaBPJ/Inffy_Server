package org.inffy.domain.chatroom.service;

import lombok.RequiredArgsConstructor;
import org.inffy.domain.chatroom.repository.ChatroomRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatroomService {

    private final ChatroomRepository chatroomRepository;
}
