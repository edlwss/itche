package ru.itche.petproject.frontendservice.student.client;

import ru.itche.petproject.frontendservice.student.controller.payload.NewStudentPayload;
import ru.itche.petproject.frontendservice.student.entityRecord.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRestClient {
    List<Student> getAllStudents();

    void createStudent(NewStudentPayload payload);

    Optional<Student> findStudent(int studentId);

    void deleteStudent(int studentId);
}
