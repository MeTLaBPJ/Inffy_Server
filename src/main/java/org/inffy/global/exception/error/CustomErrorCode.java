package org.inffy.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode{

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "[서버] Internal Server Error"),

    INVALID_PARAMS(HttpStatus.BAD_REQUEST, 400, "유효하지 않은 요청 데이터");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}