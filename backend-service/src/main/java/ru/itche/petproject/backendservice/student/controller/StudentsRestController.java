package ru.itche.petproject.backendservice.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.itche.petproject.backendservice.student.controller.payload.NewStudentPayload;
import ru.itche.petproject.backendservice.student.entity.Student;
import ru.itche.petproject.backendservice.student.service.StudentService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("musical-school-api/students")
public class StudentsRestController {

    private final StudentService studentService;

    @GetMapping
    public Iterable<Student> findStudents() {
        return this.studentService.getAllStudents();
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody NewStudentPayload payload,
                                               UriComponentsBuilder uriComponentsBuilder) {


        Student student = studentService.createStudent(payload);

        return ResponseEntity.created(uriComponentsBuilder
                        .replacePath("/musical-school-api/students/student/{studentId}")
                        .build(Map.of("studentId", student.getId())))
                .body(student);
    }
}