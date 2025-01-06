package org.inffy.domain.fcm.service;

import lombok.RequiredArgsConstructor;
import org.inffy.domain.fcm.dto.req.FcmRequestDto;
import org.inffy.domain.member.entity.Member;
import org.inffy.domain.member.repository.MemberRepository;
import org.inffy.global.exception.entity.RestApiException;
import org.inffy.global.exception.error.CustomErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmService {

    private final MemberRepository memberRepository;

    public boolean saveFcmToken(FcmRequestDto req){
        Member member = memberRepository.findBySchoolEmail(req.getSchoolEmail())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.MEMBER_NOT_FOUND));

        member.updateFcmToken(req.getFcmToken());

        return true;
    }
}
