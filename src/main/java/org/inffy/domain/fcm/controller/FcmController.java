package org.inffy.domain.fcm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inffy.domain.common.dto.ResponseDto;
import org.inffy.domain.fcm.dto.req.FcmRequestDto;
import org.inffy.domain.fcm.service.FcmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Fcm", description = "Fcm API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fcm")
public class FcmController {

    private final FcmService fcmService;

    @Operation(summary = "Fcm 토큰 저장", description = "Fcm 토큰 저장")
    @PostMapping
    public ResponseEntity<ResponseDto<Boolean>> saveFcmToken(@Valid @RequestBody FcmRequestDto req){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(fcmService.saveFcmToken(req), "FCM 토큰 저장 완료"));
    }
}
