package ru.itche.petproject.frontendservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Lesson {
    private Integer id;
//    private Timetable timetable;
//    private Course course;
//    private Group group;
    private LocalTime timeLesson;
}
