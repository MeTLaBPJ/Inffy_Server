package org.inffy.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inffy.domain.common.dto.ResponseDto;
import org.inffy.domain.member.dto.req.LoginRequestDto;
import org.inffy.domain.member.dto.req.SignupRequestDto;
import org.inffy.domain.member.dto.res.SignupResponseDto;
import org.inffy.domain.member.service.MemberService;
import org.inffy.global.security.jwt.dto.JwtTokenRequestDto;
import org.inffy.global.security.jwt.dto.JwtTokenResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<SignupResponseDto>> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(memberService.signup(signupRequestDto), "Sign Up Success"));
    }

    // 이메일 중복 확인
    @GetMapping("/signup/email/{email}")
    public ResponseEntity<ResponseDto<Boolean>> checkEmailDuplicated(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(memberService.checkEmailDuplicated(email), "Valid Email"));
    }

    // 닉네임 중복 확인
    @GetMapping("/signup/nickname/{nickname}")
    public ResponseEntity<ResponseDto<Boolean>> checkNicknameDuplicated(@PathVariable String nickname) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(memberService.checkNicknameDuplicated(nickname), "Valid Nickname"));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<JwtTokenResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(memberService.login(loginRequestDto), "Login Success"));
    }

    // 재발급
    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto<JwtTokenResponseDto>> refresh(@Valid @RequestBody JwtTokenRequestDto jwtTokenRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(memberService.reissue(jwtTokenRequestDto), "Refresh Success"));
    }
}