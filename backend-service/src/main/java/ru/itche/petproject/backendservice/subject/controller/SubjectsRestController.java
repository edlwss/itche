package ru.itche.petproject.backendservice.subject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.itche.petproject.backendservice.subject.controller.payload.NewSubjectPayload;
import ru.itche.petproject.backendservice.subject.entity.Subject;
import ru.itche.petproject.backendservice.subject.service.SubjectService;

import java.util.List;
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
        Subject subject = this.subjectService.createSubject(payload.title(), payload.titleSyllabus(),
                payload.teacherId());
        return ResponseEntity.created(uriComponentsBuilder
                        .replacePath("/musical-school-api/subjects/subject/{subjectId}")
                        .build(Map.of("subjectId", subject.getId())))
                .body(subject);
    }

    @GetMapping("{teacherId:\\d+}")
    public Iterable<Subject> findSubjectsByTeacher(@PathVariable Integer teacherId) {
        return this.subjectService.getSubjectsByTeacher(teacherId);
    }

    @PatchMapping("{teacherId:\\d+}")
    public void changeTeacher(@PathVariable Integer teacherId, @RequestBody List<Integer> subjectsId) {
        this.subjectService.chageTeacher(teacherId, subjectsId);
    }

    @GetMapping("/teachers")
    public Map<Integer, List<Subject>> getTeachersAndSubjects() {
        return this.subjectService.getSubjectsGroupedByTeacher();
    }

    @GetMapping("/teachers/pdf")
    public ResponseEntity<byte[]> generatePdfReport() {

        // Генерация PDF отчета
        byte[] pdfBytes = subjectService.generatePdf();

        // Настройка заголовков для ответа
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "SubjectsReport_Teachers"  + ".pdf");

        // Возвращение PDF в виде байтового массива
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
