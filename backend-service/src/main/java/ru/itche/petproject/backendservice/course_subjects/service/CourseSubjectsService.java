package ru.itche.petproject.backendservice.course_subjects.service;

import ru.itche.petproject.backendservice.subject.entity.Subject;

import java.util.List;
import java.util.Map;

public interface CourseSubjectsService {

    Map<String, List<Subject>> getSubjectsByCourse(Integer courseId);

    void addSubjectsToCourse(Integer courseId, List<Integer> subjectsIds);

    void deleteSubjectToCourse(int courseId, int subjectId);
}
