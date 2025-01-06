package ru.itche.petproject.backendservice.configseq;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;

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
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username) // Устанавливаем имя пользователя
                .setIssuedAt(now) // Дата выпуска токена
                .claim("role", role)
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

    // Извлечение роли из токена
    public String getRoleFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Извлекаем роль из токена
            return claims.get("role", String.class);
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Invalid JWT token or unable to extract role: " + e.getMessage());
            return null; // Возвращаем null, если что-то пошло не так
        }
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
