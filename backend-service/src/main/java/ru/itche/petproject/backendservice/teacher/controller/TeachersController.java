package ru.itche.petproject.backendservice.teacher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.itche.petproject.backendservice.teacher.controller.payload.NewTeacherPayload;
import ru.itche.petproject.backendservice.teacher.entity.Teacher;
import ru.itche.petproject.backendservice.teacher.service.TeacherService;

import java.util.Map;

@RestController
@RequestMapping("musical-school-api/teachers")
@RequiredArgsConstructor
public class TeachersController {

    private final TeacherService teacherService;

    @GetMapping
    public Iterable<Teacher> getTeachers() {
        return this.teacherService.getAllTeachers();
    }

    @PostMapping
    public ResponseEntity<Teacher> addTeacher(@RequestBody NewTeacherPayload payload,
                              UriComponentsBuilder uriComponentsBuilder) {


        Teacher teacher = teacherService.createTeacher(payload);

        return ResponseEntity.created(uriComponentsBuilder
                        .replacePath("/musical-school-api/teachers/teacher/{teacherId}")
                        .build(Map.of("teacherId", teacher.getId())))
                .body(teacher);

    }
}