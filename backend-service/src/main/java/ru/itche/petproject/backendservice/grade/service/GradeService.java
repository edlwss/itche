package ru.itche.petproject.backendservice.grade.service;

import ru.itche.petproject.backendservice.grade.entity.Grade;

import java.util.Map;

public interface GradeService {
    Iterable<Grade> getGradeByGroup(Integer groupId, Integer subjectId);

    Iterable<Grade> createGrades(Integer groupId, Integer subjectId, Map<String, String> estimation,
                                 Map<String, String> presence);

    Map<String, Double> getGradeByStudentId(Integer studentId);
}
