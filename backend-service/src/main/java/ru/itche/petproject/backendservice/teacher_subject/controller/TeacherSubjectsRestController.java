package ru.itche.petproject.backendservice.teacher_subject.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itche.petproject.backendservice.course.entity.Course;
import ru.itche.petproject.backendservice.subject.entity.Subject;
import ru.itche.petproject.backendservice.teacher_subject.service.TeacherSubjectsService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("musical-school-api/teacher-subjects/{teacherId:\\d}")
@RequiredArgsConstructor
public class TeacherSubjectsRestController {

    private final TeacherSubjectsService courseTeacherService;

    @GetMapping
    public Map<String, List<Subject>> findSubjectsByTeacher(@PathVariable("teacherId") Integer teacherId) {
        return this.courseTeacherService.getCoursesByTeacher(teacherId);
    }

    @PostMapping("add")
    private ResponseEntity<?> addSubjectsToTeacher(@PathVariable("teacherId") Integer teacherId,
                                                  @RequestBody List<Integer> coursesIds ) {
        this.courseTeacherService.addCoursesToCourse(teacherId, coursesIds);
        return ResponseEntity.noContent()
                .build();
    }
}
