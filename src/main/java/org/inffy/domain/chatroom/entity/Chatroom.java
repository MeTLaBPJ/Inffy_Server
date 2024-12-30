package org.inffy.domain.chatroom.entity;

import org.inffy.domain.chat.entity.Chat;
import org.inffy.domain.chatroom.enums.RoomType;
import org.inffy.domain.common.entity.BaseEntity;
import org.inffy.domain.member.entity.Member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chatroom")
public class Chatroom extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", nullable = false)
    private Member host;

    private LocalDate deadLine;

    @Column(name = "max_members", nullable = false)
    private Integer maxMembers;

    @Column(name = "max_male_members")
    private Integer maxMaleMembers;

    @Column(name = "max_female_members")
    private Integer maxFemaleMembers;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @Column(name = "is_started")
    private Boolean isStarted = false;

    @OneToMany(mappedBy = "chatroom")
    private List<ChatJoin> chatJoins = new ArrayList<>();

    @OneToMany(mappedBy = "chatroom")
    private List<Chat> chats = new ArrayList<>();
}