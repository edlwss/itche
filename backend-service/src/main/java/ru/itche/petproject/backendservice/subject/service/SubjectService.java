package ru.itche.petproject.backendservice.subject.service;

import ru.itche.petproject.backendservice.subject.entity.Subject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SubjectService {

    Optional<Subject> findSubject(Integer subjectId);

    void updateSubject(Integer subjectId, String title, String titleSyllabus, Integer teacherId);

    void deleteSubject(Integer subjectId);

    Iterable<Subject> getAllSubjects();

    Subject createSubject(String title, String titleSyllabus, Integer teacherId);

    Iterable<Subject> getSubjectsByTeacher(Integer teacherId);

    void chageTeacher(Integer teacherId, List<Integer> subjectsId);

    Map<Integer, List<Subject>> getSubjectsGroupedByTeacher();

    byte[] generatePdf();
}
