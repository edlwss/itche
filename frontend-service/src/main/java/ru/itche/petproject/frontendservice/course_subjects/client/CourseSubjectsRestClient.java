package ru.itche.petproject.frontendservice.course_subjects.client;

import ru.itche.petproject.frontendservice.course.entityRecord.Course;
import ru.itche.petproject.frontendservice.course_subjects.entityRecord.CourseSubjects;
import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourseSubjectsRestClient {

    void addSubjectsToCourse(Integer courseId, List<Integer> subjectIds);

    Map<String, List<Subject>> getSubjectsByCourse(Integer courseId);

    void deleteSubjectsFromCourse(Integer courseId, Integer subjectId);
}
