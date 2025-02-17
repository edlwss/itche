package ru.itche.petproject.backendservice.user.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itche.petproject.backendservice.adress.entity.Address;
import ru.itche.petproject.backendservice.id_card.entity.IdCard;


import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(schema = "musical_school", name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_user")
    @SequenceGenerator(name = "generator_user", sequenceName = "musical_school.user_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "last_name")
    @NotNull
    @Size(max = 100)
    private String lastName;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String firstName;

    @Column(name = "middle_name")
    @NotNull
    @Size(max = 100)
    private String middleName;

    @Column(name = "data_of_birth")
    @NotNull
    private LocalDate dateOfBirth;

    @Column(name = "photo")
    private String photo;

    @Column(name = "phone_number")
    @NotNull
    @Size(max = 11)
    @Pattern(regexp = "^79\\d{9}$", message = "Формат номера неверный. Пример: 79999999999")
    private String phoneNumber;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "username")
    @NotNull
    @Size(min = 1, max = 100)

    private String username;

    @Column(name = "password")
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idCard")
    private IdCard idCard;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address")
    private Address address;




}
