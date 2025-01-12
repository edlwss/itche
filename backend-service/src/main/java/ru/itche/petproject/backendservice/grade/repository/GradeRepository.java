package ru.itche.petproject.backendservice.grade.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itche.petproject.backendservice.grade.entity.Grade;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends CrudRepository<Grade, Integer> {

    @Query(value = """
        SELECT g.* 
        FROM musical_school.grade g
        JOIN musical_school.lesson l ON g.lesson_id = l.id
        JOIN musical_school.student s ON g.student_id = s.id
        WHERE s.group = :groupId AND l.id = :lessonId
    """, nativeQuery = true)
    Iterable<Grade> findGradesByGroupAndSubject(@Param("groupId") Integer groupId,
                                            @Param("lessonId") Integer subjectId);

    @Query(value = """
        SELECT * 
        FROM musical_school.grade g
        WHERE g.student_id = :studentId AND g.lesson_id = :lessonId""", nativeQuery = true)
    Optional<Grade> findByStudentAndLesson(@Param("studentId") Integer studentId, @Param("lessonId") Integer lessonId);

    @Query(value = """
        SELECT s.title AS subjectTitle, g.estimation AS grade
        FROM musical_school.grade g
        JOIN musical_school.lesson l ON g.lesson_id = l.id
        JOIN musical_school.subject s ON l.subject = s.id
        WHERE g.student_id = :studentId
    """, nativeQuery = true)
    List<Object[]> findSubjectsAndGradesByStudentId(@Param("studentId") Integer studentId);
}
