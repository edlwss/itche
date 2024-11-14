package ru.itche.petproject.frontendservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Subject {
    private Integer id;
    private String title;
    private String titleSyllabus;
    private Date updateDate;
//    private String status;
}
