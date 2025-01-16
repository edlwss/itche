package ru.itche.petproject.backendservice.grade.service;

import org.springframework.http.ResponseEntity;
import ru.itche.petproject.backendservice.grade.entity.Grade;

import java.util.List;
import java.util.Map;

public interface GradeService {
    Iterable<Grade> getGradeByGroup(Integer groupId, Integer subjectId);

    Iterable<Grade> createGrades(Integer groupId, Integer subjectId, Map<String, String> estimation,
                                 Map<String, String> presence);

    Map<String, List<Number>> getGradeByStudentId(Integer studentId);
    byte[] generatePdf(Integer studentId, Map<String, List<Number>> grades);
}
