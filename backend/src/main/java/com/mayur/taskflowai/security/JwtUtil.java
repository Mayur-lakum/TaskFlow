package com.mayur.taskflowai.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // 🟢 Secret key used to sign the token
    private static final SecretKey SECRET_KEY =
            Keys.hmacShaKeyFor(
                    "mysecretkeymysecretkeymysecretkey123456".getBytes()
            );

    // 🟢 Token validity (24 hours)
    private static final long EXPIRATION =
            1000 * 60 * 60 * 24;

    // 🔵 Generate JWT
    public String generateToken(String username) {

        return Jwts.builder()

                .subject(username)

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis() + EXPIRATION
                        )
                )

                .signWith(SECRET_KEY)

                .compact();
    }

    // 🟣 Extract username
    public String extractUsername(String token) {

        Claims claims = Jwts.parser()

                .verifyWith(SECRET_KEY)

                .build()

                .parseSignedClaims(token)

                .getPayload();

        return claims.getSubject();
    }

    // 🟠 Validate token
    public boolean isTokenValid(String token,
                                String username) {

        return extractUsername(token)
                .equals(username);
    }

}