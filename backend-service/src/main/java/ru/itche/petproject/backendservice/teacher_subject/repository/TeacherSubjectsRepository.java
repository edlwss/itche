package ru.itche.petproject.backendservice.teacher_subject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itche.petproject.backendservice.teacher_subject.entity.TeacherSubjects;

import java.util.List;

@Repository
public interface TeacherSubjectsRepository extends CrudRepository<TeacherSubjects, Integer> {

    @Query(value = """
            SELECT
                CONCAT(u.last_name, ' ', u.name) AS fullName,
                c.id AS subjectId,
                c.title AS subjectTitle
            FROM
                musical_school.teacher_subjects ct
            JOIN
                musical_school.teacher t ON ct.teacher = t.id
            JOIN
                musical_school.user u ON t.user = u.id
            JOIN
                musical_school.subject c ON ct.subject = c.id
            WHERE
                t.id = :teacherId
            """, nativeQuery = true)
    List<Object[]> findCoursesByTeacherId(@Param("teacherId") Integer teacherId);

}

