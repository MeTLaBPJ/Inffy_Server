package org.inffy.domain.chat.entity;

import lombok.*;
import org.inffy.domain.chat.enums.ChatType;
import org.inffy.domain.chatroom.entity.Chatroom;
import org.inffy.domain.common.entity.BaseEntity;

import jakarta.persistence.*;
import org.inffy.domain.member.entity.Member;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat")
public class Chat extends BaseEntity {

    @Column(nullable = false)
    private String senderNickname;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ChatType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false)
    private Chatroom chatroom;
}