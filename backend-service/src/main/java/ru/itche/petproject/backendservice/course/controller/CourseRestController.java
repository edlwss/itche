package ru.itche.petproject.backendservice.course.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itche.petproject.backendservice.course.controller.payload.UpdateCoursePayload;
import ru.itche.petproject.backendservice.course.entity.Course;
import ru.itche.petproject.backendservice.course.service.CourseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("musical-school-api/courses/course/{courseId:\\d+}")
public class CourseRestController {

    private final CourseService courseService;

    @ModelAttribute("course")
    public Course getCourse(@PathVariable("courseId") int courseId) {
        return this.courseService.findCourse(courseId).orElseThrow();
    }

    @GetMapping
    public Course findCourse(@ModelAttribute("course") Course course) {
        return course;
    }

    @PatchMapping
    public ResponseEntity<?> updateCourse(@PathVariable("courseId") int courseId,
                                           @RequestBody UpdateCoursePayload payload){

        this.courseService.updateCourse(courseId, payload.title(), payload.titleCurriculum());
        return ResponseEntity.noContent()
                .build();

    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCourse(@PathVariable("courseId") int courseId) {
        this.courseService.deleteCourse(courseId);
        return ResponseEntity.noContent()
                .build();
    }

}
