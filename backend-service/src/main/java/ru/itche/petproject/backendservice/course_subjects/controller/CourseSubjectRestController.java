package ru.itche.petproject.backendservice.course_subjects.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itche.petproject.backendservice.course_subjects.service.CourseSubjectsService;
import ru.itche.petproject.backendservice.subject.entity.Subject;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("musical-school-api/course-subjects/{courseId:\\d+}")
@RequiredArgsConstructor
public class CourseSubjectRestController {

    private final CourseSubjectsService courseSubjectsService;

    @GetMapping
    public Map<String, List<Subject>> findSubjectsByCourse(@PathVariable Integer courseId) {
        return this.courseSubjectsService.getSubjectsByCourse(courseId);
    }

//    @PostMapping
//    public ResponseEntity<?> createAllSubjectsToCourse(@PathVariable("courSubId") int courSubId,
//                                                       @RequestBody NewCourseSubjectsPayload payload) {
//        this.courseSubjectsService.setAllSubjectsToCourse(payload.courseId(courSubId), payload.subjects());
//        return ResponseEntity.noContent().build();
//    }
//    @ModelAttribute("courseSubjects")
//    public CourseSubjects getCourseSubjects(@PathVariable("courSubId") int courSubId) {
//        return this.courseSubjectsService.findCourseSubjects(courSubId).orElseThrow();
//    }
//
//    @GetMapping
//    public CourseSubjects findCourseSubjects(@ModelAttribute("courseSubjects") CourseSubjects courseSubjects) {
//        return courseSubjects;
//    }
//
//    @PatchMapping
//    public ResponseEntity<?> updateCourseSubjects(@PathVariable("courSubId") int courSubId,
//                                          @RequestBody UpdateCourseSubjectsPayload payload){
//
//        this.courseSubjectsService.updateCourseSubjects(courSubId, payload.details());
//        return ResponseEntity.noContent()
//                .build();
//
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Void> deleteCourseSubjects(@PathVariable("courSubId") int courSubId) {
//        this.courseSubjectsService.deleteCourseSubjects(courSubId);
//        return ResponseEntity.noContent()
//                .build();
//    }
}
