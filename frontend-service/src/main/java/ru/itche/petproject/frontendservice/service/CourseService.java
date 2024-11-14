package ru.itche.petproject.frontendservice.service;

import ru.itche.petproject.frontendservice.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> getAllCourses();

    Course createCourse(String title, String titleCurriculum);

    Optional<Course> findCourse(Integer courseId);

    void updateCourse(Integer id, String tittle, String titleCurriculum);

    void deteleCourse(Integer id);
}
