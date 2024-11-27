package ru.itche.petproject.backendservice.course_subjects.controller;

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
import ru.itche.petproject.backendservice.course.controller.payload.UpdateCoursePayload;
import ru.itche.petproject.backendservice.course.entity.Course;
import ru.itche.petproject.backendservice.course_subjects.controller.payload.UpdateCourseSubjectsPayload;
import ru.itche.petproject.backendservice.course_subjects.entity.CourseSubjects;
import ru.itche.petproject.backendservice.course_subjects.service.CourseSubjectsService;

@RestController
@RequestMapping("musical-school-api/course-subjects/{courSubId:\\d+}")
@RequiredArgsConstructor
public class CourseSubjectRestController {

    private final CourseSubjectsService courseSubjectsService;

    @ModelAttribute("courseSubjects")
    public CourseSubjects getCourseSubjects(@PathVariable("courSubId") int courSubId) {
        return this.courseSubjectsService.findCourseSubjects(courSubId).orElseThrow();
    }

    @GetMapping
    public CourseSubjects findCourseSubjects(@ModelAttribute("courseSubjects") CourseSubjects courseSubjects) {
        return courseSubjects;
    }

    @PatchMapping
    public ResponseEntity<?> updateCourseSubjects(@PathVariable("courSubId") int courSubId,
                                          @RequestBody UpdateCourseSubjectsPayload payload){

        this.courseSubjectsService.updateCourseSubjects(courSubId, payload.details());
        return ResponseEntity.noContent()
                .build();

    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCourseSubjects(@PathVariable("courSubId") int courSubId) {
        this.courseSubjectsService.deleteCourseSubjects(courSubId);
        return ResponseEntity.noContent()
                .build();
    }
}
