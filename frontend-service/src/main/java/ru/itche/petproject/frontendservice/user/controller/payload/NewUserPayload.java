package ru.itche.petproject.frontendservice.user.controller.payload;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.time.LocalDate;

public record NewUserPayload(String lastName,
                             String firstName,
                             String middleName,
                             LocalDate dateOfBirth,
                             String photo,
                             String phoneNumber,
                             String email,
                             String username,
                             String password,
                             AddressPayload addressPayload,
                             IdCardPayload idCardPayload) {
}
