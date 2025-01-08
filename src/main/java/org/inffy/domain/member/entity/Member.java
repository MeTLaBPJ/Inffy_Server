package org.inffy.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.inffy.domain.chatroom.entity.ChatJoin;
import org.inffy.domain.chatroom.entity.Chatroom;
import org.inffy.domain.common.entity.BaseEntity;
import org.inffy.domain.member.dto.req.SignupRequestDto;
import org.inffy.domain.member.enums.Gender;
import org.inffy.domain.member.enums.Mbti;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity implements UserDetails {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "school_email", unique = true, nullable = false)
    private String schoolEmail;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer ticket;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(name = "student_id",nullable = false)
    private String studentId;

    @Column(nullable = false)
    private String college;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private LocalDateTime birthday;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Mbti mbti;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private MemberDetail memberDetail;

    @OneToMany(mappedBy = "host")
    private List<Chatroom> hostedRooms = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatJoin> chatJoins = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() { return this.password; }

    @Override
    public String getUsername() { return this.username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public void addChatJoin(ChatJoin chatJoin){
        this.chatJoins.add(chatJoin);
    }

    public void useTicket(){this.ticket--;}

    @Builder
    public Member(SignupRequestDto signupRequestDto, String encodedPwd) {
        this.username = signupRequestDto.getUsername();
        this.nickname = signupRequestDto.getNickname();
        this.schoolEmail = signupRequestDto.getSchoolEmail();
        this.password = encodedPwd;
        this.ticket = 3;
        this.gender = signupRequestDto.getGender();
        this.studentId = signupRequestDto.getStudentId();
        this.college = signupRequestDto.getCollege();
        this.department = signupRequestDto.getDepartment();
        this.birthday = signupRequestDto.getBirthday();
        this.mbti = signupRequestDto.getMbti();
    }

    public String getEncodedPwd() {
        return this.password;
    }
}