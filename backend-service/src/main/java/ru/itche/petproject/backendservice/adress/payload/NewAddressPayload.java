package ru.itche.petproject.backendservice.adress.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record NewAddressPayload(String city,
                               String street,
                               String home,
                               String flat) {
}
