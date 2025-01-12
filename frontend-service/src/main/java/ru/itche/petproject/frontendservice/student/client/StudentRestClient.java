package ru.itche.petproject.frontendservice.student.client;
import ru.itche.petproject.frontendservice.student.entityRecord.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRestClient {
    List<Student> getAllStudents();

    void createStudent(Integer groupId,
                       String details,
                       String firstName,
                       String lastName,
                       String middleName,
                       LocalDate dateOfBirth,
                       String photo,
                       String phoneNumber,
                       String email,
                       String username,
                       String password);

    Optional<Student> findStudent(int studentId);

    void deleteStudent(int studentId);

    void updateStudent(int studentId,
                               Integer group,
                               String details,
                               String firstName,
                               String lastName,
                               String middleName,
                               LocalDate dateOfBirth,
                               String photo,
                               String phoneNumber,
                               String email);
}
