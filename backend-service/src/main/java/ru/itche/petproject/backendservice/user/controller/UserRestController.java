package ru.itche.petproject.backendservice.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.itche.petproject.backendservice.student.controller.payload.NewStudentPayload;
import ru.itche.petproject.backendservice.student.controller.payload.UpdateStudentPayload;
import ru.itche.petproject.backendservice.student.entity.Student;
import ru.itche.petproject.backendservice.user.controller.payload.NewUserPayload;
import ru.itche.petproject.backendservice.user.controller.payload.UpdateUserPayload;
import ru.itche.petproject.backendservice.user.entity.Role;
import ru.itche.petproject.backendservice.user.entity.User;
import ru.itche.petproject.backendservice.user.repository.RoleRepository;
import ru.itche.petproject.backendservice.user.service.UserService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("musical-school-api/admin")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping("/{adminId}")
    public User findAdmin(@PathVariable Integer adminId) {
        return this.userService.findAdmin(adminId).orElseThrow();
    }

    @PostMapping
    public ResponseEntity<User> createAdmin(@RequestBody NewUserPayload payload,
                                                UriComponentsBuilder uriComponentsBuilder) {

        Role studentRole = roleRepository.findByNameRole("ROLE_ADMIN")
                .orElseThrow(() -> new IllegalStateException("Role STUDENT not found"));

        User admin = userService.createUser(payload.lastName(),
                payload.firstName(),
                payload.middleName(),
                payload.dateOfBirth(),
                payload.photo(),
                payload.phoneNumber(),
                payload.email(),
                payload.username(),
                payload.password(),
                studentRole,
                payload.cardPayload().passportSeries(),
                payload.cardPayload().passportNumber(),
                payload.cardPayload().issuedBy(),
                payload.cardPayload().birthCertificateNumber(),
                payload.cardPayload().issueDate());

        return ResponseEntity.created(uriComponentsBuilder
                        .replacePath("/musical-school-api/admin/{userId}")
                        .build(Map.of("userId", admin.getId())))
                .body(admin);
    }

    @PatchMapping("/{userId:\\d+}")
    public ResponseEntity<?> updateAdmin(@PathVariable("userId") int userId,
                                           @RequestBody UpdateUserPayload payload){

        this.userService.updateUser(userId,
                payload.lastName(),
                payload.firstName(),
                payload.middleName(),
                payload.dateOfBirth(),
                payload.photo(),
                payload.phoneNumber(),
                payload.email(),
                payload.cardPayload().passportSeries(),
                payload.cardPayload().passportNumber(),
                payload.cardPayload().issuedBy(),
                payload.cardPayload().birthCertificateNumber(),
                payload.cardPayload().issueDate());

        return ResponseEntity.noContent()
                .build();

    }
}
