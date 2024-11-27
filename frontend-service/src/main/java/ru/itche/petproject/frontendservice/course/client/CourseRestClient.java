package ru.itche.petproject.frontendservice.course.client;

import ru.itche.petproject.frontendservice.course.entityRecord.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRestClient {

    List<Course> findAllCourses();

    void createCourse(String title, String curriculum);

    Optional<Course> findCourse(int courseId);

    void updateCourse(int courseId, String title, String curriculum);

    void deleteCourse(int courseId);
}
