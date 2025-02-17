package org.inffy.domain.member.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.inffy.domain.common.dto.ResponseDto;
import org.inffy.domain.member.dto.req.LoginRequestDto;
import org.inffy.domain.member.dto.req.SignupRequestDto;
import org.inffy.domain.member.dto.res.LoginResponseDto;
import org.inffy.domain.member.dto.res.SignupResponseDto;
import org.inffy.global.security.jwt.dto.JwtTokenRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface MemberApiSpecification {

    @Operation(summary = "회원가입", description = "학교 이메일을 기준으로 회원가입")
    @PostMapping
    ResponseEntity<ResponseDto<SignupResponseDto>> signup(@Valid @RequestBody SignupRequestDto signupRequestDto);

    @Operation(summary = "이메일 중복 확인", description = "학교 이메일을 기준으로 중복 확인")
    @PostMapping
    ResponseEntity<ResponseDto<Boolean>> checkEmailDuplicated(@PathVariable String email);

    @Operation(summary = "닉네임 중복 확인", description = "닉네임을 기준으로 중복 확인")
    @PostMapping
    ResponseEntity<ResponseDto<Boolean>> checkNicknameDuplicated(@PathVariable String nickname);

    @Operation(summary = "로그인", description = "학교 이메일을 id로 로그인")
    @PostMapping
    ResponseEntity<ResponseDto<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto);

    @Operation(summary = "토큰 리프레시", description = "Jwt 토큰을 리프레시")
    @PostMapping
    ResponseEntity<ResponseDto<LoginResponseDto>> refresh(@Valid @RequestBody JwtTokenRequestDto jwtTokenRequestDto);
}