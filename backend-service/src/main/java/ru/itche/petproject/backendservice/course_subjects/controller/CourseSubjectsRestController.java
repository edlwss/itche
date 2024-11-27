package ru.itche.petproject.backendservice.course_subjects.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.itche.petproject.backendservice.course.controller.payload.NewCoursePayload;
import ru.itche.petproject.backendservice.course.entity.Course;
import ru.itche.petproject.backendservice.course.service.CourseService;
import ru.itche.petproject.backendservice.course_subjects.controller.payload.NewCourseSubjectsPayload;
import ru.itche.petproject.backendservice.course_subjects.entity.CourseSubjects;
import ru.itche.petproject.backendservice.course_subjects.service.CourseSubjectsService;

@RestController
@RequestMapping("musical-school-api/course-subjects")
@RequiredArgsConstructor
public class CourseSubjectsRestController {

    private final CourseSubjectsService courseSubjectsService;

    @GetMapping
    public Iterable<CourseSubjects> findCoursesSubjects() {
        return this.courseSubjectsService.getAllCourseSubjects();
    }

    @PostMapping
    public ResponseEntity<?> createAllSubjectsToCourse(@RequestBody NewCourseSubjectsPayload payload) {
        this.courseSubjectsService.setAllSubjectsToCourse(payload.courseId(), payload.subjects());
        return ResponseEntity.noContent().build();
    }

}
