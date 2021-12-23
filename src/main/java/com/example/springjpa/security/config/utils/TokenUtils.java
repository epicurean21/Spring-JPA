package com.example.springjpa.security.config.utils;

import com.example.springjpa.security.entity.User;
import com.example.springjpa.security.entity.enums.UserRole;
import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenUtils {
    @Value("${jwt.token.secretKey}")
    private static String SECRET_KEY;

    public static String generateJwtToken(User user) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(user.getEmail())
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setExpiration(createExpireDateForOneYear())
                .signWith(SignatureAlgorithm.HS512, createSigningKey());

        return builder.compact();
    }

    public static boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            log.info("expire Time: " + claims.getExpiration());
            log.info("email: " + claims.get("email"));
            log.info("role: " + claims.get("role"));
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token Expired!");
            return false;
        } catch (JwtException e) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException e) {
            log.error("Token is Null");
            return false;
        }
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
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private static Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).parseClaimsJws(token).getBody();
    }

    private static String getUserEmailFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return (String) claims.get("email");
    }

    private static UserRole getRoleFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return (UserRole) claims.get("role");
    }

}
