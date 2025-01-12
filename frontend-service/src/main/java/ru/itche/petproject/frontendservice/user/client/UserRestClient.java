package ru.itche.petproject.frontendservice.user.client;

import ru.itche.petproject.frontendservice.user.entityRecord.User;
import ru.itche.petproject.frontendservice.user.entityRecord.UserToken;

import java.util.Optional;

public interface UserRestClient {

    UserToken authenticate(String username, String password);

    Optional<User> findAdmin(int adminId);
}
