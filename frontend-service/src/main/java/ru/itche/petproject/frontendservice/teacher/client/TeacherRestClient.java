package ru.itche.petproject.frontendservice.teacher.client;
import ru.itche.petproject.frontendservice.teacher.entityRecord.Teacher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TeacherRestClient {

    List<Teacher> getAllTeachers();

    void createTeacher(String education,
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

    Optional<Teacher> findTeacher(int teacherId);

    void deleteTeacher(int teacherId);

    void updateTeacher(int teacherId,
                       String education,
                       String details,
                       String firstName,
                       String lastName,
                       String middleName,
                       LocalDate dateOfBirth,
                       String photo,
                       String phoneNumber,
                       String email);
}
