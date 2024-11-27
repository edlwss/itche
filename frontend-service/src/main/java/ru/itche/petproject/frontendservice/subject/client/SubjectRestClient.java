package ru.itche.petproject.frontendservice.subject.client;

import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;

import java.util.Optional;

public interface SubjectRestClient {

    Optional<Subject> findSubject(int subjectId);

    void updateSubject(int subjectId, String title, String titleSyllabus);

    void deleteSubject(int subjectId);

    Iterable<Subject> getAllSubjects();

    void createSubject(String title, String titleSyllabus);
}
