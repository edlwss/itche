package ru.itche.petproject.backendservice.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itche.petproject.backendservice.adress.entity.Address;
import ru.itche.petproject.backendservice.adress.payload.NewAddressPayload;
import ru.itche.petproject.backendservice.adress.payload.UpdateAddressPayload;
import ru.itche.petproject.backendservice.adress.service.AddressService;
import ru.itche.petproject.backendservice.id_card.entity.IdCard;
import ru.itche.petproject.backendservice.id_card.service.IdCardService;
import ru.itche.petproject.backendservice.user.entity.Role;
import ru.itche.petproject.backendservice.user.entity.User;
import ru.itche.petproject.backendservice.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final IdCardService idCardService;
    private final AddressService addressService;

    @Override
    @Transactional
    public User createUser(String lastName, String firstName, String middleName,
                           LocalDate dateOfBirth, String photo, String phoneNumber,
                           String email, String username, String password, Role role,
                           String passportSeries, String passportNumber, String issuedBy,
                           String birthCertificateNumber, LocalDate issueDate, String city,
                           String street, String home, String flat) {

        IdCard idCard = idCardService.createIdcard(passportSeries, passportNumber,issuedBy,
                birthCertificateNumber, issueDate);

        Address address = addressService.createAddress(
                new NewAddressPayload(city, street, home, flat));

        User user = new User();
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setMiddleName(middleName);
        user.setDateOfBirth(dateOfBirth);
        user.setPhoto(photo);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setUsername(username);
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user.setRole(role);
        user.setIdCard(idCard);
        user.setAddress(address);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(Integer id, String lastName, String firstName, String middleName,
                           LocalDate dateOfBirth, String photo, String phoneNumber, String email,
                           String passportSeries, String passportNumber, String issuedBy,
                           String birthCertificateNumber, LocalDate issueDate, String city,
                           String street, String home, String flat) {

        this.userRepository.findById(id)
                .ifPresent(user -> {
                    user.setLastName(lastName);
                    user.setFirstName(firstName);
                    user.setMiddleName(middleName);
                    user.setDateOfBirth(dateOfBirth);
                    user.setPhoto(photo);
                    user.setPhoneNumber(phoneNumber);
                    user.setEmail(email);

                    Integer idCardId = user.getIdCard().getId();
                    this.idCardService.updateIdCard(idCardId, passportSeries, passportNumber, issuedBy,
                            birthCertificateNumber, issueDate);

                    Integer addressId = user.getAddress().getId();
                    this.addressService.updateAddress(addressId,
                            new UpdateAddressPayload(city, street, home, flat));

                });

    }

    @Override
    public Optional<User> findAdmin(Integer adminId) {
        return this.userRepository.findById(adminId);
    }

}
