package ru.itche.petproject.frontendservice.user.entityRecord;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.time.LocalDate;

public record User(Integer id,
                   String lastName,
                   String firstName,
                   String middleName,
                   LocalDate dateOfBirth,
                   String photo,
                   String phoneNumber,
                   String email,
                   String username,
                   String password,
                   Address address,
                   IdCard idCard) {
}
