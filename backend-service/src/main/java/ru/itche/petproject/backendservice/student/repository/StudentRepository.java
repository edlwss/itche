package ru.itche.petproject.backendservice.student.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itche.petproject.backendservice.student.entity.Student;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {

    @Query(value = "SELECT s.id FROM musical_school.student s WHERE s.user = :userId", nativeQuery = true)
    Integer findStudentIdByUserId(@Param("userId") Integer userId);

}
