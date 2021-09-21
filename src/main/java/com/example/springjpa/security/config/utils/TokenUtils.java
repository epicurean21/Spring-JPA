package com.example.springjpa.security.config.utils;

import com.example.springjpa.security.entity.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenUtils {
    @Value("${jwt.token.secretKey}")
    private static final String secretKey;

    public static String generateJwtToken(User user) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(user.getEmail())
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setExpiration(createExpireDateForOneYear())
                .signWith(SignatureAlgorithm.HS512, createSigningKey());

        return builder.compact();
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        /**
         * Header
         * 1. Type: JWT
         * 2. Algorithm (Encode): HS256, HMAC with SHA-256
         */

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    private static Map<String, Object> createClaims(User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());

        return claims;
    }

    private static Date createExpireDateForOneYear() {
        /**
         * Token Expiration Date
         * For example
         * c.add(Calander.DATE, 30) : 30일 설정
         */

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 30);
        return c.getTime();
    }

    private static Key createSigningKey() {
        byte[] apiKeyScreteBytes = DatatypeConverter.parseBase64Binary(secretKey);
        return new SecretKeySpec(apiKeyScreteBytes, SignatureAlgorithm.HS256.getJcaName());
    }

}
