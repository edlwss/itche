package ru.itche.petproject.backendservice.id_card.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itche.petproject.backendservice.id_card.entity.IdCard;
import ru.itche.petproject.backendservice.id_card.repository.IdCardRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class DefaultIdCardService implements IdCardService {

    private final IdCardRepository idCardRepository;

    @Override
    @Transactional
    public IdCard createIdcard(String passportSeries,
                               String passportNumber,
                               String issuedBy,
                               String birthCertificateNumber,
                               Date issueDate){
        IdCard idCard = new IdCard();
        idCard.setPassportSeries(passportSeries);
        idCard.setPassportNumber(passportNumber);
        idCard.setIssuedBy(issuedBy);
        idCard.setBirthCertificateNumber(birthCertificateNumber);
        idCard.setIssueDate(issueDate);

        return idCardRepository.save(idCard);
    }

    @Override
    @Transactional
    public void updateIdCard(Integer idCard,
                             String passportSeries,
                             String passportNumber,
                             String issuedBy,
                             String birthCertificateNumber,
                             Date issueDate) {

        this.idCardRepository.findById(idCard)
                .ifPresent(idCard1 -> {
                    idCard1.setPassportSeries(passportSeries);
                    idCard1.setPassportNumber(passportNumber);
                    idCard1.setIssuedBy(issuedBy);
                    idCard1.setBirthCertificateNumber(birthCertificateNumber);
                    idCard1.setIssueDate(issueDate);
                });
    }
}
