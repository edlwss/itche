package ru.itche.petproject.frontendservice.teacher.client;
import ru.itche.petproject.frontendservice.teacher.entityRecord.Teacher;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TeacherRestClient {

    List<Teacher> getAllTeachers();

    Teacher createTeacher(String education,
                       String details,
                       String firstName,
                       String lastName,
                       String middleName,
                       LocalDate dateOfBirth,
                       String photo,
                       String phoneNumber,
                       String email,
                       String username,
                       String password,
                       String passportNumber, String issuedBy,
                       String birthCertificateNumber, LocalDate issueDate,
                       String city, String street, String home,
                       String flat, String passportSeries);

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
                       String email,
                       String passportNumber, String issuedBy,
                       String birthCertificateNumber, LocalDate issueDate,
                       String city, String street, String home,
                       String flat, String passportSeries);
}
