package ru.itche.petproject.backendservice.course.service;

import ru.itche.petproject.backendservice.course.entity.Course;

import java.util.Optional;

public interface CourseService {

    Iterable<Course> getAllCourses();

    Course createCourse(String title, String titleCurriculum);

    Optional<Course> findCourse(Integer courseId);

    void updateCourse(Integer id, String tittle, String titleCurriculum);

    void deleteCourse(Integer id);
}
