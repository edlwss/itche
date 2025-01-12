package ru.itche.petproject.frontendservice.subject.client;

import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectRestClient {

    Optional<Subject> findSubject(int subjectId);

    void updateSubject(int subjectId, String title, String titleSyllabus, Integer teacherId);

    void deleteSubject(int subjectId);

    Iterable<Subject> getAllSubjects();

    void createSubject(String title, String titleSyllabus, Integer teacherId);

    List<Subject> getSubjectsByTeacher(Integer teacherId);

    void updateChageTeacher(Integer teacherId, List<Integer> subjectIds);
}
