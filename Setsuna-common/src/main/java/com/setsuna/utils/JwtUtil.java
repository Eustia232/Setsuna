package com.setsuna.utils;

import com.setsuna.exception.UnAuthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

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
        try {
            String id = Jwts
                    .parser()
                    .verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload()
                    .get("id")
                    .toString();
        } catch (ExpiredJwtException e) {
            throw new UnAuthorizedException("token过期");
        } catch (JwtException e) {
            throw new UnAuthorizedException("111");
        }
        return null;
    }

}
