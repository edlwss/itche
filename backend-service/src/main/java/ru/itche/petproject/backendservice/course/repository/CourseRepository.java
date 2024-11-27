package ru.itche.petproject.backendservice.course.repository;

import org.springframework.data.repository.CrudRepository;
import ru.itche.petproject.backendservice.course.entity.Course;


public interface CourseRepository extends CrudRepository<Course, Integer> {

}
