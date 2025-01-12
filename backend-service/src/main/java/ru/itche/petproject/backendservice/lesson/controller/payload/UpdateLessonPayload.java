package ru.itche.petproject.backendservice.lesson.controller.payload;

import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateLessonPayload (Integer group,
                                   Integer subject,
                                   LocalTime timeLesson,
                                   LocalDate dateLesson) {
}
