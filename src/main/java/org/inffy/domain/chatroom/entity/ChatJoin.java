package org.inffy.domain.chatroom.entity;

import lombok.*;
import org.inffy.domain.common.entity.BaseEntity;
import org.inffy.domain.member.entity.Member;

import jakarta.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_join")
public class ChatJoin extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false)
    private Chatroom chatroom;
}