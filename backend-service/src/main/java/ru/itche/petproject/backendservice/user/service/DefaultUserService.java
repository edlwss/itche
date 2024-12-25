package ru.itche.petproject.backendservice.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itche.petproject.backendservice.user.entity.Role;
import ru.itche.petproject.backendservice.user.entity.User;
import ru.itche.petproject.backendservice.user.repository.UserRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public User createUser(String lastName, String firstName, String middleName,
                           LocalDate dateOfBirth, String photo, String phoneNumber,
                           String email, String username, String password, Role role) {
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
        user.setPassword(encodedPassword); // Пароль рекомендуется хэшировать
        user.setRole(role);

        return userRepository.save(user);

    }
}
