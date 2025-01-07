package org.inffy.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode{

    // Global
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 에러 발생"),

    //FCM
    FCM_INITIALIZE_FAILED(HttpStatus.FAILED_DEPENDENCY, 424, "FCM 연결 실패"),
    FCM_MESSAGING_FAILED(HttpStatus.BAD_REQUEST, 400, "FCM 알림 전송 실패"),

    // Chatroom
    CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "채팅룸 조회에 실패하였습니다."),
    CHATROOM_PERMISSION_DENIED(HttpStatus.FORBIDDEN, 403, "채팅룸 조회에 대한 권한이 없습니다."),
    CHATROOM_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, 400, "입장할 수 없는 상태의 채팅룸입니다."),
    CHATROOM_EXPIRED(HttpStatus.BAD_REQUEST, 400, "이미 종료된 채팅룸입니다."),
    CHATROOM_IS_FULL(HttpStatus.BAD_REQUEST, 400, "인원이 모두 충족되어 입장할 수 없습니다."),
    CHATROOM_NOT_FULL(HttpStatus.BAD_REQUEST, 400, "인원이 충족되지 않아 시작할 수 없습니다."),
    MEMBER_TICKET_NOT_ENOUGH(HttpStatus.BAD_REQUEST, 400, "채팅룸 입장에 필요한 티켓이 부족합니다."),
    MEMBER_ALREADY_PARTICIPATED(HttpStatus.BAD_REQUEST, 400, "동일한 채팅룸은 한번만 입장이 가능합니다."),
    MEMBER_NOT_IN_CHATROOM(HttpStatus.BAD_REQUEST, 400, "해당 채팅룸에 속해있지 않아 나갈 수 없습니다."),

    // jwt Exception
    JWT_NOT_VALID(HttpStatus.UNAUTHORIZED, 401, "[Jwt] 유효하지 않은 Jwt"),
    JWT_ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, 419, "[Jwt] 만료된 엑세스 토큰입니다"),
    JWT_REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, 420, "[Jwt] 만료된 리프레시 토큰입니다"),
    JWT_MALFORMED(HttpStatus.UNAUTHORIZED, 401, "[Jwt] 잘못된 토큰 형식입니다"),
    JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, 401, "[Jwt] 유효하지 않은 서명입니다"),
    JWT_UNSUPPORTED(HttpStatus.UNAUTHORIZED, 401, "[Jwt] 지원하지 않는 토큰입니다"),
    JWT_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "[Jwt] 리프레시 토큰 조회 실패"),
    JWT_NOT_MATCH(HttpStatus.BAD_REQUEST, 400, "[Jwt] 리프레시 토큰 불일치"),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "맴버를 찾을 수 없습니다."),

    // RequestBody
    INVALID_PARAMS(HttpStatus.BAD_REQUEST, 400, "유효하지 않은 데이터가 전송되었습니다.");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}