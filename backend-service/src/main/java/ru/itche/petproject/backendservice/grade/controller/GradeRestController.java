package ru.itche.petproject.backendservice.grade.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.itche.petproject.backendservice.grade.entity.Grade;
import ru.itche.petproject.backendservice.grade.service.GradeService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/musical-school-api/grade")
public class GradeRestController {

    private final GradeService gradeService;

    @GetMapping()
    public Iterable<Grade> getGradesByGroup(@RequestParam Integer groupId,
                                            @RequestParam Integer lessonId) {
        return this.gradeService.getGradeByGroup(groupId, lessonId);
    }

    @PostMapping
    public ResponseEntity<Iterable<Grade>> getGradesByGroup(@RequestParam Integer groupId,
                                                           @RequestParam Integer lessonId,
                                                            @RequestBody Map<String, Map<String, String>> data,
                                                           UriComponentsBuilder uriBuilder) {

        Map<String, String> estimation = data.get("estimation");
        Map<String, String> presence = data.get("presence");

        Iterable<Grade> grades = gradeService.createGrades(groupId, lessonId, estimation, presence);

        // Возвращаем ResponseEntity с созданным URI и телом ответа
        return ResponseEntity.created(uriBuilder
                .replacePath("/musical-school-api/grade")
                .queryParam("groupId", groupId)
                .queryParam("lessonId",lessonId)
                .build().toUri())
                .body(grades);
    }

    @GetMapping("/{studentId:\\d+}")
    public Map<String, List<Number>> getGradeByStudentId(@PathVariable Integer studentId){
        return this.gradeService.getGradeByStudentId(studentId);
    }

    @GetMapping("/{studentId:\\d+}/pdf")
    public ResponseEntity<byte[]> generatePdfReport(@PathVariable Integer studentId) {

        Map<String, List<Number>> grades = gradeService.getGradeByStudentId(studentId);

        byte[] pdfBytes = gradeService.generatePdf(studentId, grades);

        // Настройка заголовков для ответа
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "GradeReport_" + studentId + ".pdf");

        // Возвращение PDF в виде байтового массива
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }


}
