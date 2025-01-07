package org.inffy.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.inffy.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

}
