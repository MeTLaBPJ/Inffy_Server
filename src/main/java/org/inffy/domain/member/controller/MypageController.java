package org.inffy.domain.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inffy.domain.common.dto.ResponseDto;
import org.inffy.domain.member.api.MypageApiSpecification;
import org.inffy.domain.member.dto.req.MemberRequestDto;
import org.inffy.domain.member.dto.res.MemberResponseDto;
import org.inffy.domain.member.entity.Member;
import org.inffy.domain.member.service.MypageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Mypage", description = "Mypage API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MypageController implements MypageApiSpecification {

    private final MypageService mypageService;

    // 멤버 조회
    @GetMapping
    public ResponseEntity<ResponseDto<MemberResponseDto>> getMember(@AuthenticationPrincipal Member member) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(mypageService.getMember(member), "View Member Information"));
    }

    // 멤버 수정
    @PutMapping
    public ResponseEntity<ResponseDto<MemberResponseDto>> updateMember(@AuthenticationPrincipal Member member, @Valid @RequestBody MemberRequestDto memberRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(mypageService.updateMember(member, memberRequestDto), "Update Member Information"));
    }
}
