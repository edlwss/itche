package ru.itche.petproject.frontendservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Teacher {
    private Integer id;
//    private User user;
    private String education;
    private int workExperience;

}
