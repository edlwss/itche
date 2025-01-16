package ru.itche.petproject.backendservice.course_subjects.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public Map<String, List<Subject>> findSubjectsByCourse(@PathVariable("courseId") Integer courseId) {
        return this.courseSubjectsService.getSubjectsByCourse(courseId);
    }

    @PostMapping("add")
    private ResponseEntity<?> addSubjectsToCourse(@PathVariable("courseId") Integer courseId,
                                                  @RequestBody List<Integer> subjectsIds ) {
        this.courseSubjectsService.addSubjectsToCourse(courseId, subjectsIds);
        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/{subjectId:\\d+}")
    public ResponseEntity<Void> deleteSubjectToCourse(@PathVariable("courseId") int courseId,
                                                      @PathVariable("subjectId") int subjectId) {
        this.courseSubjectsService.deleteSubjectToCourse(courseId, subjectId);
        return ResponseEntity.noContent()
                .build();
    }
}
