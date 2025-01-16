package ru.itche.petproject.backendservice.user.controller.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.itche.petproject.backendservice.adress.payload.NewAddressPayload;
import ru.itche.petproject.backendservice.id_card.controller.payload.NewIdCardPayload;
import ru.itche.petproject.backendservice.lesson.controller.payload.NewLessonPayload;
import ru.itche.petproject.backendservice.user.entity.Role;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record NewUserPayload(String lastName,
                             String firstName,
                             String middleName,
                             LocalDate dateOfBirth,
                             String photo,
                             String phoneNumber,
                             String email,
                             String username,
                             String password,
                             NewIdCardPayload cardPayload,
                             NewAddressPayload addressPayload) {
}
