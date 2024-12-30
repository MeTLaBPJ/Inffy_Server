package org.inffy.global.exception.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.inffy.global.exception.error.ErrorCode;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException{
    private final ErrorCode errorCode;
}