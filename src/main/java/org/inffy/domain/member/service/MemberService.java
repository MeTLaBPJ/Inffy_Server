package org.inffy.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.inffy.domain.member.dto.req.LoginRequestDto;
import org.inffy.domain.member.dto.req.SignupRequestDto;
import org.inffy.domain.member.dto.res.LoginResponseDto;
import org.inffy.domain.member.dto.res.SignupResponseDto;
import org.inffy.domain.member.entity.Member;
import org.inffy.domain.member.entity.MemberRole;
import org.inffy.domain.member.repository.MemberRepository;
import org.inffy.domain.member.repository.MemberRoleRepository;
import org.inffy.global.exception.entity.RestApiException;
import org.inffy.global.exception.error.CustomErrorCode;
import org.inffy.global.security.jwt.dto.JwtTokenRequestDto;
import org.inffy.global.security.jwt.entity.RefreshToken;
import org.inffy.global.security.jwt.repository.RefreshTokenRepository;
import org.inffy.global.security.jwt.util.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        if (!signupRequestDto.getPassword().equals(signupRequestDto.getPasswordCheck())){
            throw new RestApiException(CustomErrorCode.PASSWORD_NOT_CORRECT);
        }

        Member member = new Member(signupRequestDto, passwordEncoder.encode(signupRequestDto.getPassword()));

        memberRepository.save(member);

        MemberRole memberRole = MemberRole.builder()
                .member(member)
                .roles("ROLE_USER")
                .build();

        memberRoleRepository.save(memberRole);

        return SignupResponseDto.builder()
                .schoolEmail(member.getSchoolEmail())
                .username(member.getUsername())
                .build();
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Member member = memberRepository.findBySchoolEmail(loginRequestDto.getSchoolEmail())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.MEMBER_NOT_FOUND));

        boolean matchPassword = passwordEncoder.matches(loginRequestDto.getPassword(), member.getEncodedPwd());

        if (!matchPassword) {
            throw new RestApiException(CustomErrorCode.PASSWORD_NOT_CORRECT);
        }

        // 1. SchoolEmail/Password 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthenticationToken();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        // authenticationManagerBuilder.getObject() : 구성된 AuthenticationManager 반환
        // authenticate(authenticationToken) : 사용자 인증 시도 -> 성공 시 Authentication 객체 반환
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        if (!authentication.isAuthenticated()) {
            throw new RestApiException(CustomErrorCode.LOGIN_FAILED);
        }

        refreshTokenRepository.deleteByUsername(authentication.getName());

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        LoginResponseDto loginResponseDto = jwtTokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .username(authentication.getName())
                .refreshToken(loginResponseDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);

        return loginResponseDto;
    }

    @Transactional
    public LoginResponseDto reissue(JwtTokenRequestDto jwtTokenRequestDto) {

        // 리프레시 토큰 검증
        if (!jwtTokenProvider.validateToken(jwtTokenRequestDto.getRefreshToken())){
            throw new RestApiException(CustomErrorCode.JWT_REFRESH_TOKEN_EXPIRED);
        }

        // 인증 정보 추출
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtTokenRequestDto.getRefreshToken());

        // 저장소에서 리프레시 토큰 조회
        RefreshToken refreshToken = refreshTokenRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.JWT_NOT_FOUND));

        // 리프레시 토큰 일치 여부 확인
        if (!refreshToken.getRefreshToken().equals(jwtTokenRequestDto.getRefreshToken())) {
            throw new RestApiException(CustomErrorCode.JWT_NOT_MATCH);
        }

        // 새로운 토큰 발급
        LoginResponseDto loginResponseDto1 = jwtTokenProvider.generateTokenDto(authentication);

        // 리프레시 토큰 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(loginResponseDto1.getRefreshToken());

        refreshTokenRepository.save(newRefreshToken);

        return loginResponseDto1;
    }

    @Transactional
    public Boolean checkEmailDuplicated(String email) {
        if (memberRepository.existsBySchoolEmail(email))
            throw new RestApiException(CustomErrorCode.EMAIL_DUPLICATED);
        return true;
    }

    @Transactional
    public Boolean checkNicknameDuplicated(String nickname) {
        if (memberRepository.existsByNickname(nickname))
            throw new RestApiException(CustomErrorCode.NICKNAME_DUPLICATED);
        return true;
    }

    @Transactional
    public void initializeTicket() {
        memberRepository.initializeAllTickets();
    }
}
