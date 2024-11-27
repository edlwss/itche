package ru.itche.petproject.backendservice.subject.service;

import ru.itche.petproject.backendservice.subject.entity.Subject;

import java.util.Optional;

public interface SubjectService {

    Optional<Subject> findSubject(Integer subjectId);

    void updateSubject(Integer subjectId, String title, String titleSyllabus);

    void deleteSubject(Integer subjectId);

    Iterable<Subject> getAllSubjects();

    Subject createSubject(String title, String titleSyllabus);
}
