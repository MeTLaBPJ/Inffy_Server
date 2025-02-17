package org.inffy.global.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.inffy.global.security.jwt.util.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String jwt = resolveToken(request);
        String requestURI = request.getRequestURI();

        // 회원가입, 로그인 로직에 대해서 JWT 토큰 검증을 건너뜀
        if (
                   requestURI.startsWith("/signup") || requestURI.startsWith("/login")
                || requestURI.startsWith("/mailSend") || requestURI.startsWith("/mailAuthCheck")
                || requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/swagger-resources")
                || requestURI.startsWith("/api/v1/auth") || requestURI.startsWith("/v3/api-docs")
                || requestURI.startsWith("/webjars") || requestURI.startsWith("/swagger-ui.html")
                || requestURI.startsWith("/ws") ) {

            filterChain.doFilter(request, response);
            return;
        }

        // JWT 토큰 null 확인, 유효성 검증
        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Security Context : " + authentication.getName() + "\nURI : " + requestURI);
        } else {
            System.out.println("유효한 JWT 토큰이 없습니다, URI : " + requestURI);
        }

        // 다음 필터로 요청, 응답 전달
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        // Authorization 헤더에서 토큰 추출
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        // 토큰이 유효한지 확인
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            // Bearer 제외한 실제 토큰 반환
            return bearerToken.substring(7);
        }

        return null;
    }
}
