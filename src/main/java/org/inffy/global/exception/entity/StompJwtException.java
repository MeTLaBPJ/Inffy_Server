package org.inffy.global.exception.entity;

import io.jsonwebtoken.JwtException;
import lombok.Getter;
import org.inffy.global.exception.error.CustomErrorCode;
import org.inffy.global.exception.error.ErrorCode;

@Getter
public class StompJwtException extends JwtException {

    private final ErrorCode errorCode;

    public StompJwtException(CustomErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public StompJwtException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}