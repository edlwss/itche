package ru.itche.petproject.backendservice.teacher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itche.petproject.backendservice.teacher.controller.payload.UpdateTeacherPayload;
import ru.itche.petproject.backendservice.teacher.entity.Teacher;
import ru.itche.petproject.backendservice.teacher.service.TeacherService;

@RestController
@RequestMapping("musical-school-api/teachers/teacher/{teacherId:\\d+}")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @ModelAttribute("teacher")
    public Teacher getTeacher (@PathVariable("teacherId") int teacherId) {
        return this.teacherService.findTeacher(teacherId).orElseThrow(null);
    }

    @GetMapping
    public Teacher findTeacher(@ModelAttribute("teacher") Teacher teacher) {
        return teacher;
    }

    @PatchMapping
    public ResponseEntity<?> updateTeacher(@PathVariable("teacherId") int teacherId,
                                           @RequestBody UpdateTeacherPayload payload){

        this.teacherService.updateTeacher(teacherId, payload);
        System.out.println(payload);
        return ResponseEntity.noContent()
                .build();

    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTeacher(@PathVariable("teacherId") int teacherId) {
        this.teacherService.deleteTeacher(teacherId);
        return ResponseEntity.noContent()
                .build();
    }

}
