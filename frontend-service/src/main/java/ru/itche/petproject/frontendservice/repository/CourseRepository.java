package ru.itche.petproject.frontendservice.repository;

import org.springframework.stereotype.Repository;
import ru.itche.petproject.frontendservice.entity.Course;

import java.util.List;
import java.util.Optional;


public interface CourseRepository {
    List<Course> findAll();

    Course save(Course course);

    Optional<Course> findById(Integer courseId);

    void deleteById(Integer id);
}
