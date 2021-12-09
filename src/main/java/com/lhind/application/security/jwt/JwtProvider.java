package com.lhind.application.security.jwt;

import com.lhind.application.exception.InvalidTokenException;
import com.lhind.application.security.logout.LoggedOutJwtTokenCache;
import com.lhind.application.security.logout.OnUserLogoutSuccessEvent;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final LoggedOutJwtTokenCache loggedOutJwtTokenCache;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public String generateToken(Authentication authResult) {
        return Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateTokenIsForALoggedOutUser(String token) {
        log.info("Validating token is not for a logged out user.");

        OnUserLogoutSuccessEvent previouslyLoggedOutEvent = loggedOutJwtTokenCache.getLogoutEventForToken(token);
        if (previouslyLoggedOutEvent != null) {
            log.error("Token belongs to a logged out user.");

            throw new InvalidTokenException();
        }

        return true;
    }

    public String getAuthorizationHeader() {
        return jwtConfig.getAuthorizationHeader();
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public String getTokenPrefix() {
        return jwtConfig.getTokenPrefix();
    }
}
