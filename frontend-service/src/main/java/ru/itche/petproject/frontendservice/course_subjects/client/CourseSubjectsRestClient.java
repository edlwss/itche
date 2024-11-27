package ru.itche.petproject.frontendservice.course_subjects.client;

import ru.itche.petproject.frontendservice.course_subjects.entityRecord.CourseSubjects;

import java.util.List;
import java.util.Optional;

public interface CourseSubjectsRestClient {

    Iterable<CourseSubjects> getAllCourseSubjects();

    void setAllSubjectsToCourse(Integer courseId, List<Integer> subjectIds);

    Optional<CourseSubjects> findCourseSubjects(int courSubId);

    void updateCourseSubjects(int courSubId, String details);

    void deleteCourseSubjects(int courSubId);
}
