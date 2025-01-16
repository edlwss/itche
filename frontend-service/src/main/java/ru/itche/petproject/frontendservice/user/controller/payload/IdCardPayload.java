package ru.itche.petproject.frontendservice.user.controller.payload;

import java.time.LocalDate;
import java.util.Date;

public record IdCardPayload (String passportSeries,
                            String passportNumber,
                            String issuedBy,
                            String birthCertificateNumber,
                            LocalDate issueDate){
}
