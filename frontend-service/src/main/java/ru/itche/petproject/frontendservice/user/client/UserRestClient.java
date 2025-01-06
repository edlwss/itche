package ru.itche.petproject.frontendservice.user.client;

import ru.itche.petproject.frontendservice.user.entityRecord.UserToken;

public interface UserRestClient {

    UserToken authenticate(String username, String password);

    String getUserRoleFromServer();

}
