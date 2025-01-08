package org.inffy.global.security.jwt.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.inffy.domain.member.dto.res.LoginResponseDto;
import org.inffy.domain.member.entity.Member;
import org.inffy.domain.member.repository.MemberRepository;
import org.inffy.global.exception.entity.RestApiException;
import org.inffy.global.exception.error.CustomErrorCode;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private final long accessTokenValidityInMilliSeconds;
    private final long refreshTokenValidityInMilliSeconds;
    private final String secret;
    private final MemberRepository memberRepository;
    private Key key;

    public JwtTokenProvider(@Value("${jwt.secretKey}") String secret, @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds, MemberRepository memberRepository) {
        this.secret = secret;

        // Access Token: 30분
        this.accessTokenValidityInMilliSeconds = tokenValidityInSeconds * 1000;

        // Refresh Token: 7일
        this.refreshTokenValidityInMilliSeconds = tokenValidityInSeconds * 60 * 60 * 24 * 7 * 1000;
        this.memberRepository = memberRepository;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT Token 발급
    public LoginResponseDto generateTokenDto(Authentication authentication) {

        // 권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 토큰의 expire 시간을 설정
        long now = new Date().getTime();
        Date accessTime = new Date(now + this.accessTokenValidityInMilliSeconds);
        Date refreshTime = new Date(now + this.refreshTokenValidityInMilliSeconds);

        // JWT 생성
        String accessToken = Jwts.builder()
                // 토큰 주체 설정 ( username )
                .setSubject(authentication.getName())
                // 사용자 권한 정보 설정
                .claim(AUTHORITIES_KEY, authorities)
                // 토큰 서명 추가 / key : 비밀 키, HS512 : 서명 알고리즘
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(accessTime)
                // 문자열 변경
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(refreshTime)
                .compact();

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                // 자동 로그아웃 기능을 위한 간극
                .accessTokenExpires(this.accessTokenValidityInMilliSeconds - 5000)
                .accessTokenExpiresDate(accessTime)
                .build();
    }

    public Authentication getAuthentication(String token) {
        // JWT 토큰 파싱 , 클레임 추출
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token) // JWT 토큰 파싱, 서명 검증
                .getBody(); // 파싱된 JWT의 클레임 정보 추출

        Member member = memberRepository.findByUsername(claims.getSubject())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.MEMBER_NOT_FOUND));

        // 사용자 권한 정보 추출
        Collection<? extends GrantedAuthority> authorities = member.getAuthorities();

        // 인증 객체 생성 및 반환
        return new UsernamePasswordAuthenticationToken(member, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            // JWT 파서 빌더 생성 / 서명 키 설정 / 파서 생성 / JWT 파싱 및 검증
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SignatureException e) {
            throw new RestApiException(CustomErrorCode.JWT_SIGNATURE); // 유효하지 않은 서명
        } catch (MalformedJwtException e){
            throw new RestApiException(CustomErrorCode.JWT_MALFORMED); // 잘못된 토큰 형식
        } catch (ExpiredJwtException e) {
            throw new RestApiException(CustomErrorCode.JWT_REFRESH_TOKEN_EXPIRED); // 만료된 토큰
        } catch (UnsupportedJwtException e) {
            throw new RestApiException(CustomErrorCode.JWT_UNSUPPORTED); // 지원하지 않는 토큰
        } catch (IllegalArgumentException e) {
            throw new RestApiException(CustomErrorCode.JWT_NOT_VALID); // 유효하지 않은 토큰
        }
    }
}
