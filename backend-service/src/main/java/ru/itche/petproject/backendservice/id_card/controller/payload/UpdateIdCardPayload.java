package ru.itche.petproject.backendservice.id_card.controller.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateIdCardPayload (String passportSeries,
                                   String passportNumber,
                                   String issuedBy,
                                   String birthCertificateNumber,
                                   LocalDate issueDate){
}
