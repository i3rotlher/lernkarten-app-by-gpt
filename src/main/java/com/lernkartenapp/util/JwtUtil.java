package com.lernkartenapp.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String KEY;

    @Value("${jwt.experation}")
    private long EXPIRATION_TIME;
    
    public String generateToken(String userId) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + EXPIRATION_TIME;
        Date exp = new Date(expMillis);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
    }

    public String validateToken(String token) {
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
