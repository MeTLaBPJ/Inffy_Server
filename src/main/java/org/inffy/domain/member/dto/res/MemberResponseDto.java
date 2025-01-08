package org.inffy.domain.member.dto.res;

import lombok.Builder;
import lombok.Getter;
import org.inffy.domain.member.entity.Member;
import org.inffy.domain.member.entity.MemberDetail;
import org.inffy.domain.member.enums.*;

@Getter
public class MemberResponseDto {

    private String nickname;
    private String schoolEmail;
    private String college;
    private String department;
    private Mbti mbti;
    private String introduction;
    private Integer height;
    private BodyType bodyType;
    private Religion religion;
    private DrinkingHabit drinkingHabit;
    private SmokingStatus smokingStatus;

    @Builder
    private MemberResponseDto(String nickname, String schoolEmail, String college, String department, Mbti mbti, String introduction, Integer height, BodyType bodyType, Religion religion, DrinkingHabit drinkingHabit, SmokingStatus smokingStatus) {
        this.nickname = nickname;
        this.schoolEmail = schoolEmail;
        this.college = college;
        this.department = department;
        this.mbti = mbti;
        this.introduction = introduction;
        this.height = height;
        this.bodyType = bodyType;
        this.religion = religion;
        this.drinkingHabit = drinkingHabit;
        this.smokingStatus = smokingStatus;
    }

    public static MemberResponseDto ofMember(Member member) {
        return MemberResponseDto.builder()
                .nickname(member.getNickname())
                .schoolEmail(member.getSchoolEmail())
                .college(member.getCollege())
                .department(member.getDepartment())
                .mbti(member.getMbti())
                .build();
    }

    public static MemberResponseDto ofMemberWithDetail(Member member, MemberDetail memberDetail) {
        return MemberResponseDto.builder()
                .nickname(member.getNickname())
                .schoolEmail(member.getSchoolEmail())
                .college(member.getCollege())
                .department(member.getDepartment())
                .mbti(member.getMbti())
                .introduction(memberDetail.getIntroduction())
                .height(memberDetail.getHeight())
                .bodyType(memberDetail.getBodyType())
                .religion(memberDetail.getReligion())
                .drinkingHabit(memberDetail.getDrinkingHabit())
                .smokingStatus(memberDetail.getSmokingStatus())
                .build();
    }
}