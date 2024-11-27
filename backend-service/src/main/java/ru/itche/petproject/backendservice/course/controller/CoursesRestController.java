package ru.itche.petproject.backendservice.course.controller;

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
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("musical-school-api/courses")
public class CoursesRestController {

    private final CourseService courseService;

    @GetMapping
    public Iterable<Course> findCourses() {
        return this.courseService.getAllCourses();
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody NewCoursePayload payload,
                                                UriComponentsBuilder uriComponentsBuilder) {
        Course course = this.courseService.createCourse(payload.title(), payload.titleCurriculum());
        //посмотреть другие методы
        return ResponseEntity.created(uriComponentsBuilder
                .replacePath("/musical-school-api/courses/course/{courseId}")
                .build(Map.of("courseId", course.getId())))
                .body(course);
    }
}
