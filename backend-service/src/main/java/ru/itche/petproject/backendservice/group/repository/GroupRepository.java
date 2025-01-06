package ru.itche.petproject.backendservice.group.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.itche.petproject.backendservice.group.entity.Group;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Integer> {

    @Query(value = """
            SELECT
                g.title AS groupTitle,
                s.id AS studentId
            FROM
                musical_school.group g
            JOIN
                musical_school.student s ON s.group = g.id
            WHERE
                g.id = 1
            """, nativeQuery = true)
    List<Object[]> findGroupWithStudents(@Param("groupId") Integer groupId);

}
