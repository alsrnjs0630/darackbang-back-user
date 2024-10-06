package com.lab.darackbang.common.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component // Bean에서 사용할 클래스임을 나타내는 어노테이션. 다른 클래스에서 JWTUtil을 사용할 수 있게 함.
public class JWTUtil {

    // 비밀키 설정
    private final SecretKey key = Keys.hmacShaKeyFor("DarcakbangSecretKeyUserAuthentiactionKey".getBytes(StandardCharsets.UTF_8));

    // JWT 생성
    public String generateToken(Map<String, Object> claims, int expirationMinutes) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject((String) claims.get("email"))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMinutes*60*1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 검증 및 클레임 추출
    public Map<String, Object> validateToken(String token) throws ExpiredJwtException, SignatureException{
            // 토큰 검증 로직 추가
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

    }

    // 토큰 만료 여부 체크
    public boolean isTokenExpired(String token) {
        try {
            Map<String, Object> claims = validateToken(token);
            Date expiration = (Date) claims.get("exp");
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }
}
