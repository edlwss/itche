package ru.itche.petproject.backendservice.user.service;

import ru.itche.petproject.backendservice.user.entity.Role;
import ru.itche.petproject.backendservice.user.entity.User;

import java.time.LocalDate;

public interface UserService {

    User createUser(String lastName, String firstName, String middleName,
                    LocalDate dateOfBirth, String photo, String phoneNumber,
                    String email, String username, String password, Role role);


}
