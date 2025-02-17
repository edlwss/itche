package ru.itche.petproject.backendservice.group.controller.payload;

import ru.itche.petproject.backendservice.course.entity.Course;

import java.time.LocalDate;

public record NewGroupPayload(String title,
                              Integer course,
                              LocalDate startEducation,
                              LocalDate endEducation) {
}
