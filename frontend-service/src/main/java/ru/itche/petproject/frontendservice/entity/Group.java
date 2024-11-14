package ru.itche.petproject.frontendservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Group {
    private Integer id;
    private String title;
//    private Course course;
    private Date startEducation;
    private Date endEducation;

}
