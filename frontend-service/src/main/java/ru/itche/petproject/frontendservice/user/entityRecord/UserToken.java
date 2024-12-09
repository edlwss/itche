package ru.itche.petproject.frontendservice.user.entityRecord;

import lombok.Data;

@Data
public class UserToken {
    private String token;
    private String role;
}