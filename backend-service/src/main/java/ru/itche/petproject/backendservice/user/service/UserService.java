package ru.itche.petproject.backendservice.user.service;

import ru.itche.petproject.backendservice.user.entity.Role;
import ru.itche.petproject.backendservice.user.entity.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public interface UserService {

    User createUser(String lastName, String firstName, String middleName,
                    LocalDate dateOfBirth, String photo, String phoneNumber,
                    String email, String username, String password, Role role,
                    String passportSeries, String passportNumber, String issuedBy,
                    String birthCertificateNumber, Date issueDate);

    void updateUser(Integer id, String lastName, String firstName, String middleName,
                    LocalDate dateOfBirth, String photo, String phoneNumber,
                    String email, String passportSeries, String passportNumber,
                    String issuedBy, String birthCertificateNumber, Date issueDate);

    Optional<User> findAdmin(Integer adminId);
}
