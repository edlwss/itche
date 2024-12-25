package ru.itche.petproject.backendservice.configseq;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Генерация секретного ключа
    private final long jwtExpirationMs = 86400000; // Время жизни токена (24 часа)

    // Генерация токена
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username) // Устанавливаем имя пользователя
                .setIssuedAt(now) // Дата выпуска токена
                .setExpiration(expiryDate) // Дата истечения токена
                .signWith(jwtSecret) // Подпись
                .compact();
    }

    // Получение имени пользователя из токена
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Проверка токена
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Invalid JWT token: " + e.getMessage());
        }
        return false;
    }
}
