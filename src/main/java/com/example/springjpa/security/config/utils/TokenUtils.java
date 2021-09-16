package com.example.springjpa.security.config.utils;

import com.example.springjpa.security.entity.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

@Log4j2
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenUtils {
    @Value("${jwt.token.secretKey}")
    private final String secretKey;

    public static String generateJwtToken(User user) {
        JwtBuilder builder= Jwts.builder()
                .setSubject(user.getEmail())
                .setHeader(createH)
    }
}
