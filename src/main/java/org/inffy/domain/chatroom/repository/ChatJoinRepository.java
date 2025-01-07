package org.inffy.domain.chatroom.repository;

import org.inffy.domain.chatroom.entity.ChatJoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatJoinRepository extends JpaRepository<ChatJoin, Long> {

    Optional<ChatJoin> findByMemberIdAndChatroomId(Long memberId, Long chatroomId);

    Boolean existsByMemberIdAndChatroomId(Long memberId, Long chatroomId);
}
