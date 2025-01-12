package ru.itche.petproject.backendservice.subject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.itche.petproject.backendservice.subject.entity.Subject;

public interface SubjectRepository extends CrudRepository<Subject, Integer> {

    @Query(value = "SELECT * FROM musical_school.subject WHERE teacher_id = :teacherId",
            nativeQuery = true)
    Iterable<Subject> findSubjectsByTeacherId(@Param("teacherId") Integer teacherId);
}
