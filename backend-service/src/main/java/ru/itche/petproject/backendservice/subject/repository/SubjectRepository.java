package ru.itche.petproject.backendservice.subject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.itche.petproject.backendservice.subject.entity.Subject;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Integer> {

    @Query(value = "SELECT * FROM musical_school.subject WHERE teacher_id = :teacherId",
            nativeQuery = true)
    Iterable<Subject> findSubjectsByTeacherId(@Param("teacherId") Integer teacherId);

    @Query(value = "SELECT teacher_id, id AS subject_id FROM musical_school.subject WHERE teacher_id != 0", nativeQuery = true)
    List<Object[]> findTeacherAndSubjectIds();

}
