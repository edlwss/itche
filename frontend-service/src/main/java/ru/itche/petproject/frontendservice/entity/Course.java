package ru.itche.petproject.frontendservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
//@Table(name = "course")

public class Course {
//    @Id
    private Integer id;
    private String title;
    private String titleCurriculum;
//    private Date updateDate;
//    private Status status;
}
