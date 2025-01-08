package org.inffy.global.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // Spring Security에서 인증되지 않은 사용자가 보호된 리소스에 접근(401 Unauthorized)하려고 할 때 처리
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException{
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
