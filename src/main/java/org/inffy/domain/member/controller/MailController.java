package org.inffy.domain.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inffy.domain.common.dto.ResponseDto;
import org.inffy.domain.member.api.MailApiSpecification;
import org.inffy.domain.member.dto.req.EmailCheckDto;
import org.inffy.domain.member.dto.req.EmailRequestDto;
import org.inffy.domain.member.dto.res.EmailResponseDto;
import org.inffy.domain.member.service.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Mail", description = "Mail API")
@RestController
@RequiredArgsConstructor
public class MailController implements MailApiSpecification {

    private final MailService mailService;

    // 이메일 전송
    @PostMapping("/mailSend")
    public ResponseEntity<ResponseDto<EmailResponseDto>> mailSend(@RequestBody @Valid EmailRequestDto emailRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(mailService.joinEmail(emailRequestDto.getSchoolEmail()), "Send Verification Email"));
    }

    // 이메일 인증
    @PostMapping("/mailAuthCheck")
    public ResponseEntity<ResponseDto<EmailResponseDto>> mailAuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(mailService.mailAuthCheck(emailCheckDto.getSchoolEmail(), emailCheckDto.getAuthNum()), "Email Verification"));
    }
}