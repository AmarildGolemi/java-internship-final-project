package com.lhind.application.security.logout;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class LoggedOutJwtTokenCache {

    private final ExpiringMap<String, OnUserLogoutSuccessEvent> tokenEventMap;
    private final SecretKey secretKey;

    @Autowired
    public LoggedOutJwtTokenCache(SecretKey secretKey) {
        this.secretKey = secretKey;
        this.tokenEventMap = ExpiringMap.builder()
                .variableExpiration()
                .maxSize(1000)
                .build();
    }

    public void markLogoutEventForToken(OnUserLogoutSuccessEvent event) {
        String token = event.getToken();
        if (tokenEventMap.containsKey(token)) {
            log.info(String.format("Log out token for user [%s] is already present in the cache", event.getUsername()));
        } else {
            Date tokenExpiryDate = getTokenExpiryFromJWT(token);
            Long ttlForToken = getTTLForToken(tokenExpiryDate);
            tokenEventMap.put(token, event, ttlForToken, TimeUnit.SECONDS);
        }
    }

    public Date getTokenExpiryFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public OnUserLogoutSuccessEvent getLogoutEventForToken(String token) {
        return tokenEventMap.get(token);
    }

    private Long getTTLForToken(Date date) {
        Long secondAtExpiry = date.toInstant().getEpochSecond();
        Long secondAtLogout = Instant.now().getEpochSecond();

        return Math.max(0, secondAtExpiry - secondAtLogout);
    }

}