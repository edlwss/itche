package ru.itche.petproject.backendservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Student {
    private Integer id;
//    private User user;
//    private Group group;
//    private Parent parent;
    private String details;
}
