package ru.itche.petproject.backendservice.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itche.petproject.backendservice.student.controller.payload.UpdateStudentPayload;
import ru.itche.petproject.backendservice.student.entity.Student;
import ru.itche.petproject.backendservice.student.service.StudentService;


@RestController
@RequiredArgsConstructor
@RequestMapping("musical-school-api/students/student/{studentId:\\d+}")
public class StudentRestController {

    private final StudentService studentService;

    @ModelAttribute("student")
    public Student getStudent(@PathVariable("studentId") int studentId) {
        return this.studentService.findStudent(studentId).orElseThrow();
    }

    @GetMapping
    public Student findStudent(@ModelAttribute("student") Student student) {
        return student;
    }

    @PatchMapping
    public ResponseEntity<?> updateStudent(@PathVariable("studentId") int studentId,
                                          @RequestBody UpdateStudentPayload payload){

        this.studentService.updateStudent(studentId, payload.group(), payload.details());
        return ResponseEntity.noContent()
                .build();

    }

    @DeleteMapping
    public ResponseEntity<Void> deleteStudent(@PathVariable("studentId") int studentId) {
        this.studentService.deleteStudent(studentId);
        return ResponseEntity.noContent()
                .build();
    }

}
