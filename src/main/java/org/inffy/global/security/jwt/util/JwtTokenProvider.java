package org.inffy.global.security.jwt.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.inffy.global.exception.entity.StompJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.inffy.global.exception.error.CustomErrorCode;

import java.security.Key;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final long ACCESS_TOKEN_EXPIRED_TIME = 100 * 60 * 60 * 1000L;
    private static final long REFRESH_TOKEN_EXPIRED_TIME = 3 * 24 * 60 * 60 * 1000L;

    private final Key key;

    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(@Value("${jwt.secret}")String jwtSecret, UserDetailsService userDetailsService){
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.userDetailsService = userDetailsService;
    }

    public String getJwtFromStompRequest(final StompHeaderAccessor accessor){
        return accessor.getFirstNativeHeader("Authorization").substring(7);
    }

    public boolean validateStompJwt(String token) {
        if(token == null) {
            throw new JwtException("Jwt AccessToken not found");
        }

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (ExpiredJwtException e) {
            throw new StompJwtException(CustomErrorCode.JWT_ACCESS_TOKEN_EXPIRED);
        } catch (MalformedJwtException e) {
            throw new StompJwtException(CustomErrorCode.JWT_MALFORMED);
        } catch (SignatureException | SecurityException e) {
            throw new StompJwtException(CustomErrorCode.JWT_SIGNATURE);
        } catch (UnsupportedJwtException e) {
            throw new StompJwtException(CustomErrorCode.JWT_UNSUPPORTED);
        }
    }
}
