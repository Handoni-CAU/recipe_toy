package com.oviesAries.recipe.domain.user.infra;

import com.oviesAries.recipe.domain.user.exception.PasswordInvalidException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    private final TokenProvider invalidSecretKeyTokenProvider
            = new TokenProvider(
            "invalidSecretKey", 8640000L);

    @Test
    @DisplayName("토큰 생성 테스트")
    void createToken() {
        // given
        String payload = String.valueOf(1L);

        // when
        String token = tokenProvider.createToken(payload);

        // then
        assertNotNull(token);
    }

    @Test
    @DisplayName("payload 조회")
    void getPayload() {
        // given
        String payload = String.valueOf(1L);
        String token = tokenProvider.createToken(payload);

        // when
        String result = tokenProvider.getPayload(token);

        // then
        assertEquals(payload, result);
    }

    @Test
    @DisplayName("만료된 토큰 검증")
    void getExpiredToken() {
        // given
        String expiredToken = Jwts.builder()
                .setSubject("testPayload")
                .setExpiration(new java.util.Date(System.currentTimeMillis() - 1000))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secretKey)
                .compact();

        // when then
        assertThatExceptionOfType(ExpiredJwtException.class)
                .isThrownBy(() -> tokenProvider.getPayload(expiredToken));
    }

    @Test
    @DisplayName("시크릿 키가 다른 토큰 검증")
    void getInvalidSecretKeyToken() {
        // given
        String payload = String.valueOf(1L);
        String token = invalidSecretKeyTokenProvider.createToken(payload);

        // when then
        assertThatExceptionOfType(SignatureException.class)
                .isThrownBy(() -> tokenProvider.getPayload(token));
    }

}