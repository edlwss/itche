package ru.itche.petproject.backendservice.user.controller.payload;

import java.time.LocalDate;

public record UpdateUserPayload (String lastName,
                                 String firstName,
                                 String middleName,
                                 LocalDate dateOfBirth,
                                 String photo,
                                 String phoneNumber,
                                 String email){
}
