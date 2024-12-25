package ru.itche.petproject.backendservice.student.service;

import ru.itche.petproject.backendservice.group.entity.Group;
import ru.itche.petproject.backendservice.student.entity.Student;
import ru.itche.petproject.backendservice.user.entity.User;

import java.time.LocalDate;
import java.util.Optional;

public interface StudentService {
    Iterable<Student> getAllStudents();

    Student createStudent(User user, Integer groupId, String details);

    Optional<Student> findStudent(int studentId);

    void updateStudent(int studentId, Integer groupId, String details);

    void deleteStudent(int studentId);
}
