package ru.itche.petproject.backendservice.user.controller.payload;

import ru.itche.petproject.backendservice.user.entity.Role;

import java.time.LocalDate;

public record NewUserPayload(String lastName,
                             String firstName,
                             String middleName,
                             LocalDate dateOfBirth,
                             String photo,
                             String phoneNumber,
                             String email,
                             String username,
                             String password) {
}
