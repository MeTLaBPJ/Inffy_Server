package org.inffy.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.inffy.domain.member.dto.req.MemberRequestDto;
import org.inffy.domain.member.dto.res.MemberResponseDto;
import org.inffy.domain.member.entity.Member;
import org.inffy.domain.member.entity.MemberDetail;
import org.inffy.domain.member.repository.MemberDetailRepository;
import org.inffy.domain.member.repository.MemberRepository;
import org.inffy.global.exception.entity.RestApiException;
import org.inffy.global.exception.error.CustomErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final MemberRepository memberRepository;
    private final MemberDetailRepository memberDetailRepository;

    public MemberResponseDto getMember(Member member) {
        Member newMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.MEMBER_NOT_FOUND));

        MemberDetail memberDetail = newMember.getMemberDetail();

        if (memberDetail == null) {
            return MemberResponseDto.ofMember(newMember);
        } else {
            return MemberResponseDto.ofMemberWithDetail(newMember, memberDetail);
        }
    }

    @Transactional
    public MemberResponseDto updateMember(Member member, MemberRequestDto memberRequestDto) {
        Member newMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.MEMBER_NOT_FOUND));

        MemberDetail memberDetail = newMember.getMemberDetail();

        if (memberDetail == null) {
            memberDetail = new MemberDetail(memberRequestDto);
            memberDetailRepository.save(memberDetail);
        }

        memberDetail.updateMember(memberRequestDto, newMember);

        memberRepository.save(newMember);

        return MemberResponseDto.ofMemberWithDetail(newMember, memberDetail);
    }
}