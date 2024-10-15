package com.lab.darackbang.common.utils;

import com.lab.darackbang.exception.CustomJWTException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component // Bean에서 사용할 클래스임을 나타내는 어노테이션. 다른 클래스에서 JWTUtil을 사용할 수 있게 함.
public class JWTUtil {

    // 비밀키는 최소 256비트(32자) 이상이어야 합니다.
    private static final String secretString = "to-die-to-sleep-maybe-to-dream-to-be-or-not-to-be-that-is-question";

    // BASE64 인코딩을 사용하여 키가 적절히 포맷되었는지 확인할 수 있습니다.
    static SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));

    /**
     * JWT 토큰을 생성합니다.
     *
     * @param claims 토큰에 포함할 클레임들
     * @param min    토큰의 만료 시간을 설정하는 분 단위
     * @return 생성된 JWT 토큰
     * @throws CustomJWTException 토큰 생성 중 오류가 발생한 경우
     */
    public static String generateToken(Map<String, Object> claims, int min) {
        try {
            return Jwts.builder()
                    .header() // 헤더에 접근
                    .add("type", "JWT")
                    .add("alg", "HS256")
                    .and()  // 메인 빌더로 돌아감
                    .subject((String) claims.get("email"))
                    .claims(claims)
                    .issuedAt(Date.from(ZonedDateTime.now().toInstant()))
                    .expiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                    .signWith(key, Jwts.SIG.HS256)
                    .compact();
        } catch (Exception ex) {
            throw new CustomJWTException("generateToken:" + ex);
        }
    }

    /**
     * JWT 토큰에서 사용자 이름을 추출합니다.
     *
     * @param token 추출할 JWT 토큰
     * @return 추출된 사용자 이름 (이메일)
     * @throws CustomJWTException 사용자 이름 추출 중 오류가 발생한 경우
     */
    public static String getUsername(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception ex) {
            throw new CustomJWTException("getUsername:" + ex);
        }
    }

    /**
     * JWT 토큰의 유효성을 검증합니다.
     *
     * @param token 검증할 JWT 토큰
     * @return 검증된 클레임들
     * @throws CustomJWTException 토큰 검증 중 오류가 발생한 경우
     */
    public static Map<String, Object> validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception ex) {
            throw new CustomJWTException("Invalid JWT token:" + ex);
        }
    }
}
