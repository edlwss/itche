package ru.itche.petproject.backendservice.id_card.service;

import ru.itche.petproject.backendservice.id_card.entity.IdCard;

import java.util.Date;

public interface IdCardService {

    IdCard createIdcard(String passportSeries,
                        String passportNumber,
                        String issuedBy,
                        String birthCertificateNumber,
                        Date issueDate);

    void updateIdCard(Integer idCard,
                      String passportSeries,
                      String passportNumber,
                      String issuedBy,
                      String birthCertificateNumber,
                      Date issueDate);

}
