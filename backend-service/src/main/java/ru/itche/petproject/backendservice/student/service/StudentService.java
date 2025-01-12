package ru.itche.petproject.backendservice.student.service;

import ru.itche.petproject.backendservice.group.entity.Group;
import ru.itche.petproject.backendservice.student.controller.payload.NewStudentPayload;
import ru.itche.petproject.backendservice.student.controller.payload.UpdateStudentPayload;
import ru.itche.petproject.backendservice.student.entity.Student;
import ru.itche.petproject.backendservice.user.entity.User;

import java.time.LocalDate;
import java.util.Optional;

public interface StudentService {
    Iterable<Student> getAllStudents();

    Student createStudent(NewStudentPayload payload, Integer groupId);

    Optional<Student> findStudent(int studentId);

    void updateStudent(Integer studentId, UpdateStudentPayload payload);

    void deleteStudent(int studentId);
}
