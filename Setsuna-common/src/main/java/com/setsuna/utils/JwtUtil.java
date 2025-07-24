package com.setsuna.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt生成工具类
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private Duration expire;

    private Key secretKey;

    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createJWT(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + expire.toMillis());
        return Jwts
                .builder()
                .claims(map)
                .expiration(expireTime)
                .signWith(secretKey)
                .compact();
    }

    public String parseJWT(String jwt) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .get("id")
                .toString();
    }

}
