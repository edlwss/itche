package ru.itche.petproject.frontendservice.user.entityRecord;

import java.time.LocalDate;

public record IdCard (Integer id,
                      String passportSeries,
                      String passportNumber,
                      String issuedBy,
                      String birthCertificateNumber,
                      LocalDate issueDate){
}
