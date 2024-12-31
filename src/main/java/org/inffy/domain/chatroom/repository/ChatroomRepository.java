package org.inffy.domain.chatroom.repository;

import org.inffy.domain.chatroom.entity.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
}
