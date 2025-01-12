package ru.itche.petproject.backendservice.configseq;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itche.petproject.backendservice.student.repository.StudentRepository;
import ru.itche.petproject.backendservice.teacher.repository.TeacherRepository;
import ru.itche.petproject.backendservice.user.repository.UserRepository;
import ru.itche.petproject.backendservice.user.service.UserService;

import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Генерация JWT токена
        String token = jwtProvider.generateToken(authentication);

        Integer userId = userRepository.findByUsername(username).orElseThrow().getId();
        String userRole = jwtProvider.getRoleFromToken(token);

        if(userRole.equals("ROLE_STUDENT")) {
            userId = studentRepository.findStudentIdByUserId(userId);
        } else if(userRole.equals("ROLE_TEACHER")) {
            userId = teacherRepository.findTeacherIdByUserId(userId);
        }


        // Возвращаем токен клиенту
        return ResponseEntity.ok(Map.of("token", token, "userId", userId, "role", userRole));
    }
}
