package ru.itche.petproject.backendservice.teacher.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itche.petproject.backendservice.teacher.entity.Teacher;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Integer> {

    @Query(value = "SELECT s.id FROM musical_school.teacher s WHERE s.user = :userId", nativeQuery = true)
    Integer findTeacherIdByUserId(@Param("userId") Integer userId);
}
