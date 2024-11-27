package ru.itche.petproject.backendservice.course_subjects.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itche.petproject.backendservice.course_subjects.entity.CourseSubjects;

@Repository
public interface CourseSubjectsRepository extends CrudRepository<CourseSubjects, Integer> {

}
