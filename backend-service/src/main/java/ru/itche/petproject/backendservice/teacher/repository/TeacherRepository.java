package ru.itche.petproject.backendservice.teacher.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itche.petproject.backendservice.teacher.entity.Teacher;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Integer> {
}
