package ru.itche.petproject.frontendservice.course_subjects.client;

import ru.itche.petproject.frontendservice.course.entityRecord.Course;
import ru.itche.petproject.frontendservice.course_subjects.entityRecord.CourseSubjects;
import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourseSubjectsRestClient {

    Iterable<CourseSubjects> getAllCourseSubjects();

    Map<String, List<Subject>> getSubjectsByCourse(Integer courseId);

    void setAllSubjectsToCourse(Integer courseId, List<Integer> subjectIds);

    Optional<CourseSubjects> findCourseSubjects(int courSubId);

    void updateCourseSubjects(int courSubId, String details);

    void deleteCourseSubjects(int courSubId);
}
