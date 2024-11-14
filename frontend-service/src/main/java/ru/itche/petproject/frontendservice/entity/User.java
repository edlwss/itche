package ru.itche.petproject.frontendservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    private Integer id;
    private String surname;
    private String name;
    private String patronymic;
    private Date birthdate;
    private Image photo;
    private String email;
    private String phone;

//    private String login;
//    private String password;
//    private String document;
//    private String role;
//    private String address;
}

