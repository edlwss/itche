package ru.itche.petproject.frontendservice.grade.client;

import ru.itche.petproject.frontendservice.grade.entityRecord.Grade;

import java.util.List;
import java.util.Map;

public interface GradeRestClient {

    List<Grade> getGradesByGroup(Integer groupId, Integer lessonId);
    void createGrades(Integer groupId, Integer lessonId, Map<String,
            Map<String, String>> data);
    Map<String, List<Number>> getGradeByStudent(Integer studentId);
    byte[] getGradePdfByStudent(Integer studentId);
}
