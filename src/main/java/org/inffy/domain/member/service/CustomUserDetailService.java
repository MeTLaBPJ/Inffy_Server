package org.inffy.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.inffy.domain.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findBySchoolEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("유저 정보 확인 실패"));
    }
}
