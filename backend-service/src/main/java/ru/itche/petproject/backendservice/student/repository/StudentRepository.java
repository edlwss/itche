package ru.itche.petproject.backendservice.student.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itche.petproject.backendservice.student.entity.Student;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {
}
