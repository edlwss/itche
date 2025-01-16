package ru.itche.petproject.backendservice.user.controller.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.itche.petproject.backendservice.adress.payload.UpdateAddressPayload;
import ru.itche.petproject.backendservice.id_card.controller.payload.UpdateIdCardPayload;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateUserPayload (String lastName,
                                 String firstName,
                                 String middleName,
                                 LocalDate dateOfBirth,
                                 String photo,
                                 String phoneNumber,
                                 String email,
                                 UpdateIdCardPayload cardPayload,
                                 UpdateAddressPayload addressPayload) {
}
