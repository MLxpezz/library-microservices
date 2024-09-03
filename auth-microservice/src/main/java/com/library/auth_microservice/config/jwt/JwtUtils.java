package com.library.auth_microservice.config.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    public String createToken(Authentication authentication) {

        String username = authentication.getName();

        return Jwts
                .builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .issuedAt(new Date())
                .signWith(getSecretKey())
                .compact();
    }

    public String createMicroserviceToken(String microserviceName) {
        return Jwts
                .builder()
                .subject(microserviceName)
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .issuedAt(new Date())
                .signWith(getSecretKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();

            return true;

        } catch (JwtException e) {
            throw  new JwtException("Invalid JWT token");
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public SecretKey getSecretKey() {
        String secretKey = System.getenv("SECRET_KEY");
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }
}
