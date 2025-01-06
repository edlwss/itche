package ru.itche.petproject.backendservice.configseq;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Генерация JWT токена
        String token = jwtProvider.generateToken(authentication);

        // Возвращаем токен клиенту
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/role")
    public String getRoleFromToken(@RequestHeader("Authorization") String token) {
        // Убираем префикс "Bearer " из токена
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String role = jwtProvider.getRoleFromToken(token);
        if (role != null) {
            return role; // Возвращаем роль
        } else {
            return "ROLE_ANONYMOUS"; // Возвращаем роль по умолчанию
        }
    }

}
