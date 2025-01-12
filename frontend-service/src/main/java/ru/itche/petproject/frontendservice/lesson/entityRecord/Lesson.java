package ru.itche.petproject.frontendservice.lesson.entityRecord;

import ru.itche.petproject.frontendservice.group.entityRecord.Group;
import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;

import java.time.LocalDate;
import java.time.LocalTime;

public record Lesson(Integer id,
                     Group group,
                     Subject subject,
                     LocalTime timeLesson,
                     LocalDate dateLesson) {
}
