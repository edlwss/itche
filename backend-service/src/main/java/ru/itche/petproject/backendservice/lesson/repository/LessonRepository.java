package ru.itche.petproject.backendservice.lesson.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itche.petproject.backendservice.lesson.entity.Lesson;

import java.util.List;

@Repository
public interface LessonRepository extends CrudRepository<Lesson, Integer> {

    @Query(value = """
    SELECT * 
    FROM musical_school.lesson 
    WHERE group_id = :groupId
      AND EXTRACT(MONTH FROM date_lesson) = :month
      AND EXTRACT(YEAR FROM date_lesson) = :year
""", nativeQuery = true)
    List<Lesson> findLessonsByGroupAndMonth(
            @Param("groupId") Integer groupId,
            @Param("month") Integer month,
            @Param("year") Integer year
    );
}

