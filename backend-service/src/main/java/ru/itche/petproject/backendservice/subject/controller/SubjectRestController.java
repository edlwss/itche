package ru.itche.petproject.backendservice.subject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itche.petproject.backendservice.course.controller.payload.UpdateCoursePayload;
import ru.itche.petproject.backendservice.course.entity.Course;
import ru.itche.petproject.backendservice.subject.controller.payload.UpdateSubjectPayload;
import ru.itche.petproject.backendservice.subject.entity.Subject;
import ru.itche.petproject.backendservice.subject.service.SubjectService;

@RestController
@RequiredArgsConstructor
@RequestMapping("musical-school-api/subjects/subject/{subjectId:\\d+}")
public class SubjectRestController {

    private final SubjectService subjectService;

    @ModelAttribute("subject")
    public Subject getSubject (@PathVariable("subjectId") int subjectId) {
        return this.subjectService.findSubject(subjectId).orElseThrow();
    }

    @GetMapping
    public Subject findSubject (@ModelAttribute("subject") Subject subject) {
        return subject;
    }

    @PatchMapping
    public ResponseEntity<?> updateCourse(@PathVariable("subjectId") int subjectId,
                                          @RequestBody UpdateSubjectPayload payload){

        this.subjectService.updateSubject(subjectId, payload.title(), payload.titleSyllabus(),
                payload.teacherId());
        return ResponseEntity.noContent()
                .build();

    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSubject(@PathVariable("subjectId") int subjectId) {
        this.subjectService.deleteSubject(subjectId);
        return ResponseEntity.noContent()
                .build();
    }

}
