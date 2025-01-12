package ru.itche.petproject.backendservice.id_card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(schema = "musical_school", name = "idcard")
public class IdCard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_card_generator")
    @SequenceGenerator(name = "id_card_generator", sequenceName = "musical_school.idcard_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "passport_series", nullable = true)
    private String passportSeries;

    @Column(name = "passport_number", nullable = true)
    private String passportNumber;

    @Column(name = "issued_by")
    private String issuedBy;

    @Column(name = "birth_certificate_number", nullable = true)
    private String birthCertificateNumber;

    @Column(name = "issue_date")
    private Date issueDate;
}
