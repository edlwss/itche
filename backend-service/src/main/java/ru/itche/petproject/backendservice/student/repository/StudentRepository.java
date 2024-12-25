package ru.itche.petproject.backendservice.student.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itche.petproject.backendservice.student.entity.Student;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {

//    @Query(value = "SELECT s.id AS studentId, u.last_name AS lastName, u.name AS firstName, " +
//            "u.middle_name AS middleName, u.data_of_birth AS dateOfBirth, " +
//            "u.phone_number AS phoneNumber, u.email AS email, u.photo AS photo " +
//            "FROM musical_school.student s " +
//            "JOIN musical_school.user u ON s.\"user\" = u.id",
//            nativeQuery = true)
//    Iterable<Student> findAll();
//
//    @Query(value = "SELECT s.id AS studentId, u.last_name AS lastName, u.name AS firstName, " +
//            "u.middle_name AS middleName, u.data_of_birth AS dateOfBirth, " +
//            "u.phone_number AS phoneNumber, u.email AS email, u.photo AS photo " +
//            "FROM musical_school.student s " +
//            "JOIN musical_school.user u ON s.\"user\" = u.id " +
//            "WHERE s.id = :studentId",
//            nativeQuery = true)
//    Optional<Student> findById(@Param("studentId") int studentId);
}
