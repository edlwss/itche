package ru.itche.petproject.frontendservice.subject.client;

import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;
import ru.itche.petproject.frontendservice.teacher.entityRecord.Teacher;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SubjectRestClient {

    Optional<Subject> findSubject(int subjectId);

    void updateSubject(int subjectId, String title, String titleSyllabus, Integer teacherId);

    void deleteSubject(int subjectId);

    List<Subject> getAllSubjects();

    void createSubject(String title, String titleSyllabus, Integer teacherId);

    List<Subject> getSubjectsByTeacher(Integer teacherId);

    void updateChageTeacher(Integer teacherId, List<Integer> subjectIds);

    Map<Teacher, List<Subject>> getTeachersWithSubjects();

    byte[] getGradePdfBySubject();
}
