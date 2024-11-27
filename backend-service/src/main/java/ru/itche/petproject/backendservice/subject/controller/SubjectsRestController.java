package ru.itche.petproject.backendservice.subject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.itche.petproject.backendservice.subject.controller.payload.NewSubjectPayload;
import ru.itche.petproject.backendservice.subject.entity.Subject;
import ru.itche.petproject.backendservice.subject.service.SubjectService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("musical-school-api/subjects")
public class SubjectsRestController {

    private final SubjectService subjectService;

    @GetMapping
    public Iterable<Subject> findSubjects() {
        return this.subjectService.getAllSubjects();
    }

    @PostMapping
    public ResponseEntity<Subject> createSubject(@RequestBody NewSubjectPayload payload,
                                               UriComponentsBuilder uriComponentsBuilder) {
        Subject subject = this.subjectService.createSubject(payload.title(), payload.titleSyllabus());
        return ResponseEntity.created(uriComponentsBuilder
                        .replacePath("/musical-school-api/subjects/subject/{subjectId}")
                        .build(Map.of("subjectId", subject.getId())))
                .body(subject);
    }
}
