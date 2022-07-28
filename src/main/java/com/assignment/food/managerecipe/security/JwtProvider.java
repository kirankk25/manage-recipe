package com.assignment.food.managerecipe.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;

import static io.jsonwebtoken.Jwts.parser;
import static java.util.Date.from;

@Service
public class JwtProvider {
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }
    public boolean validateToken(String jwt) {
        parser().parseClaimsJwt(jwt);
        return true;
    }
    public String getUsernameFromJwt(String token) {
        Claims claims = parser()
                .parseClaimsJwt(token)
                .getBody();
        return claims.getSubject();
    }
    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}