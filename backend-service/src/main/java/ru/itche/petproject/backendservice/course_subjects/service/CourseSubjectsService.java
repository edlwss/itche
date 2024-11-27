package ru.itche.petproject.backendservice.course_subjects.service;

import ru.itche.petproject.backendservice.course_subjects.entity.CourseSubjects;

import java.util.List;
import java.util.Optional;

public interface CourseSubjectsService {

    Iterable<CourseSubjects> getAllCourseSubjects();

    void setAllSubjectsToCourse(Integer courseId, List<Integer> subjectIds);

    Optional<CourseSubjects> findCourseSubjects(int courSubId);

    void updateCourseSubjects(int courSubId, String details);

    void deleteCourseSubjects(int courSubId);
}
