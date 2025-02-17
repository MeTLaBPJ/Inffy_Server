package org.inffy.domain.member.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.inffy.domain.common.dto.ResponseDto;
import org.inffy.domain.member.dto.req.EmailCheckDto;
import org.inffy.domain.member.dto.req.EmailRequestDto;
import org.inffy.domain.member.dto.res.EmailResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface MailApiSpecification {

    @Operation(summary = "메일 전송", description = "학교 이메일으로 인증 메일 전송")
    @PostMapping
    ResponseEntity<ResponseDto<EmailResponseDto>> mailSend(@RequestBody @Valid EmailRequestDto emailRequestDto);

    @Operation(summary = "메일 인증", description = "학교 이메일을 기준으로 코드 인증")
    @PostMapping
    ResponseEntity<ResponseDto<EmailResponseDto>> mailAuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto);
}