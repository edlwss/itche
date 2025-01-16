package ru.itche.petproject.backendservice.course_subjects.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itche.petproject.backendservice.course_subjects.entity.CourseSubjects;

import java.util.List;

@Repository
public interface CourseSubjectsRepository extends CrudRepository<CourseSubjects, Integer> {
    @Query(value = """
            SELECT
                c.title AS courseTitle,
                s.id AS subjectId
            FROM
                musical_school.course_subjects cs
            JOIN
                musical_school.course c ON cs.course = c.id
            JOIN
                musical_school.subject s ON cs.subject = s.id
            WHERE
                c.id = :courseId
            """, nativeQuery = true)
    List<Object[]> findSubjectsByCourseId(@Param("courseId") Integer courseId);

    @Modifying
    @Query(value = """
            DELETE FROM musical_school.course_subjects cs
            WHERE cs.course = :courseId AND cs.subject = :subjectId""", nativeQuery = true)
    void deleteByCourseIdAndSubjectId(@Param("courseId") int courseId,
                                      @Param("subjectId") int subjectId);
}
