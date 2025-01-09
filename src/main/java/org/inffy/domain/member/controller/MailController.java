package org.inffy.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inffy.domain.common.dto.ResponseDto;
import org.inffy.domain.member.dto.req.EmailCheckDto;
import org.inffy.domain.member.dto.req.EmailRequestDto;
import org.inffy.domain.member.dto.res.EmailResponseDto;
import org.inffy.domain.member.service.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MailController {

    private final MailService mailService;

    // 이메일 전송
    @PostMapping("/mailSend")
    public ResponseEntity<ResponseDto<EmailResponseDto>> mailSend(@RequestBody @Valid EmailRequestDto emailRequestDto) {
        log.info("Received mailSend request for email: {}", emailRequestDto.getSchoolEmail());
        try {
            EmailResponseDto response = mailService.joinEmail(emailRequestDto.getSchoolEmail());
            log.info("Mail sent successfully to: {}", emailRequestDto.getSchoolEmail());
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(response, "Send Verification Email"));
        } catch (Exception e) {
            log.error("Error occurred while sending mail to: {}", emailRequestDto.getSchoolEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto.of(null, "Failed to send verification email"));
        }
    }

    // 이메일 인증
    @PostMapping("/mailAuthCheck")
    public ResponseEntity<ResponseDto<EmailResponseDto>> mailAuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(mailService.mailAuthCheck(emailCheckDto.getSchoolEmail(), emailCheckDto.getAuthNum()), "Email Verification"));
    }
}