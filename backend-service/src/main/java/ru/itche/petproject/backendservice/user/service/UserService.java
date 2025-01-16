package ru.itche.petproject.backendservice.user.service;

import ru.itche.petproject.backendservice.user.entity.Role;
import ru.itche.petproject.backendservice.user.entity.User;

import java.time.LocalDate;
import java.util.Optional;

public interface UserService {

    User createUser(String lastName, String firstName, String middleName,
                    LocalDate dateOfBirth, String photo, String phoneNumber,
                    String email, String username, String password, Role role,
                    String passportSeries, String passportNumber, String issuedBy,
                    String birthCertificateNumber, LocalDate issueDate, String city,
                    String street, String home, String flat);

    void updateUser(Integer id, String lastName, String firstName, String middleName,
                    LocalDate dateOfBirth, String photo, String phoneNumber,
                    String email, String passportSeries, String passportNumber,
                    String issuedBy, String birthCertificateNumber, LocalDate issueDate,
                    String city, String street, String home, String flat);

    Optional<User> findAdmin(Integer adminId);
}
