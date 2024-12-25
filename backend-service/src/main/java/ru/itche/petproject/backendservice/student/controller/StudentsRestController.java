package ru.itche.petproject.backendservice.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.itche.petproject.backendservice.student.controller.payload.NewStudentPayload;
import ru.itche.petproject.backendservice.student.entity.Student;
import ru.itche.petproject.backendservice.student.service.StudentService;
import ru.itche.petproject.backendservice.user.controller.payload.NewUserPayload;
import ru.itche.petproject.backendservice.user.entity.Role;
import ru.itche.petproject.backendservice.user.entity.User;
import ru.itche.petproject.backendservice.user.repository.RoleRepository;
import ru.itche.petproject.backendservice.user.service.UserService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("musical-school-api/students")
public class StudentsRestController {

    private final StudentService studentService;
    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping
    public Iterable<Student> findStudents() {
        return this.studentService.getAllStudents();
    }

    @PostMapping
    public ResponseEntity<Student> createCourse(@RequestBody NewStudentPayload payload,
                                               UriComponentsBuilder uriComponentsBuilder) {

        Role studentRole = roleRepository.findByNameRole("ROLE_STUDENT")
                .orElseThrow(() -> new IllegalStateException("Role STUDENT not found"));

        //занести в сервайс
        User user = userService.createUser(
                payload.userPayload().lastName(),
                payload.userPayload().firstName(),
                payload.userPayload().middleName(),
                payload.userPayload().dateOfBirth(),
                payload.userPayload().photo(),
                payload.userPayload().phoneNumber(),
                payload.userPayload().email(),
                payload.userPayload().username(),
                payload.userPayload().password(),
                studentRole
        );


        Student student = studentService.createStudent(
                user,
                payload.group(),
                payload.details()
        );


        return ResponseEntity.created(uriComponentsBuilder
                        .replacePath("/musical-school-api/students/{studentId}")
                        .build(Map.of("studentId", student.getId())))
                .body(student);
    }
}