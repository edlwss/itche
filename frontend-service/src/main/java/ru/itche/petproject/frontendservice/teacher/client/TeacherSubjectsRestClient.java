package ru.itche.petproject.frontendservice.teacher.client;

import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;

import java.util.List;
import java.util.Map;

public interface TeacherSubjectsRestClient {

    void addSubjectsToTeacher(Integer teacherId, List<Integer> subjectIds);

    Map<String, List<Subject>> getSubjectsByTeacher(Integer teacherId);
}
