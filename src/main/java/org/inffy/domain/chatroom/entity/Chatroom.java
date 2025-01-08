package org.inffy.domain.chatroom.entity;

import lombok.Builder;
import org.inffy.domain.chat.entity.Chat;
import org.inffy.domain.chatroom.dto.req.ChatroomCreateRequestDto;
import org.inffy.domain.chatroom.enums.RoomType;
import org.inffy.domain.common.entity.BaseEntity;
import org.inffy.domain.member.entity.Member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.inffy.domain.member.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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

    @Column(name = "dead_line", nullable = false)
    private LocalDate deadLine;

    @Column(name = "max_members", nullable = false)
    private Integer maxMembers;

    @Column(name = "max_male_members")
    private Integer maxMaleMembers;

    @Column(name = "max_female_members")
    private Integer maxFemaleMembers;

    @Column(name = "current_male_count")
    private Integer currentMaleCount = 0;

    @Column(name = "current_female_count")
    private Integer currentFemaleCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @Column(name = "is_started")
    private Boolean isStarted = false;

    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatJoin> chatJoins = new ArrayList<>();

    @OneToMany(mappedBy = "chatroom")
    private List<Chat> chats = new ArrayList<>();

    @Builder
    protected Chatroom(Member host, String title, String subtitle, Integer maxMaleMembers, Integer maxFemaleMembers){
        this.host = host;
        this.title = title;
        this.subtitle = subtitle;
        this.maxMaleMembers = maxMaleMembers;
        this.maxFemaleMembers = maxFemaleMembers;
        this.maxMembers = maxMaleMembers + maxFemaleMembers;
        this.deadLine  = LocalDate.now(ZoneId.of("Asia/Seoul")).plusDays(7);
    }

    public void incrementGenderCount(Gender gender) {
        if (gender == Gender.MALE) {
            this.currentMaleCount++;
        } else {
            this.currentFemaleCount++;
        }
    }

    public void decrementGenderCount(Gender gender) {
        if (gender == Gender.MALE) {
            this.currentMaleCount--;
        } else {
            this.currentFemaleCount--;
        }
    }

    public Integer getCurrentMemberCount(){
        return getCurrentMaleCount() + getCurrentFemaleCount();
    }

    public void addChatJoin(ChatJoin chatJoin, Gender gender){
        this.chatJoins.add(chatJoin);
        incrementGenderCount(gender);
    }

    public void start(){
        this.isStarted = true;
    }

    public Boolean isHost(Member member){
        return this.getHost().getId().equals(member.getId());
    }

    public void updateDeadLine(LocalDate deadLine){
        this.deadLine = deadLine;
    }
}