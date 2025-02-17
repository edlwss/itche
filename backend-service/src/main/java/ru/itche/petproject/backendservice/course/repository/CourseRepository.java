package ru.itche.petproject.backendservice.course.repository;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.itche.petproject.backendservice.course.entity.Course;


public interface CourseRepository extends CrudRepository<Course, Integer> {
    @Procedure(name = "musical_school.addcourse")
    Course addCourse(@Param("p_title") String title, @Param("p_title_curriculum") String titleCurriculum);
}
