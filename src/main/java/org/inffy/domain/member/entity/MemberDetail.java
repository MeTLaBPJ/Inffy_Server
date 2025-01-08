package org.inffy.domain.member.entity;

import org.inffy.domain.common.entity.BaseEntity;
import org.inffy.domain.member.dto.req.MemberRequestDto;
import org.inffy.domain.member.enums.BodyType;
import org.inffy.domain.member.enums.DrinkingHabit;
import org.inffy.domain.member.enums.Religion;
import org.inffy.domain.member.enums.SmokingStatus;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_detail")
public class MemberDetail extends BaseEntity {

    private String introduction;

    private Integer height;

    @Enumerated(EnumType.STRING)
    private BodyType bodyType;

    @Enumerated(EnumType.STRING)
    private Religion religion;

    @Enumerated(EnumType.STRING)
    private DrinkingHabit drinkingHabit;

    @Enumerated(EnumType.STRING)
    private SmokingStatus smokingStatus;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void updateMember(MemberRequestDto memberRequestDto) {
        this.introduction = memberRequestDto.getIntroduction();
        this.height = memberRequestDto.getHeight();
        this.bodyType = memberRequestDto.getBodyType();
        this.religion = memberRequestDto.getReligion();
        this.drinkingHabit = memberRequestDto.getDrinkingHabit();
        this.smokingStatus = memberRequestDto.getSmokingStatus();
    }

    public MemberDetail(MemberRequestDto memberRequestDto) {
        this.introduction = memberRequestDto.getIntroduction();
        this.height = memberRequestDto.getHeight();
        this.bodyType = memberRequestDto.getBodyType();
        this.religion = memberRequestDto.getReligion();
        this.drinkingHabit = memberRequestDto.getDrinkingHabit();
        this.smokingStatus = memberRequestDto.getSmokingStatus();
    }
}