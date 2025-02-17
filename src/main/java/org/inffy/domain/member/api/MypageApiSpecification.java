package org.inffy.domain.member.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.inffy.domain.common.dto.ResponseDto;
import org.inffy.domain.member.dto.req.MemberRequestDto;
import org.inffy.domain.member.dto.res.MemberResponseDto;
import org.inffy.domain.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface MypageApiSpecification {

    @Operation(summary = "멤버 조회", description = "멤버의 정보를 조회")
    @GetMapping
    ResponseEntity<ResponseDto<MemberResponseDto>> getMember(@AuthenticationPrincipal Member member);

    @Operation(summary = "멤버 수정", description = "멤버의 디테일 정보를 수정")
    @PutMapping
    ResponseEntity<ResponseDto<MemberResponseDto>> updateMember(@AuthenticationPrincipal Member member, @Valid @RequestBody MemberRequestDto memberRequestDto);
}