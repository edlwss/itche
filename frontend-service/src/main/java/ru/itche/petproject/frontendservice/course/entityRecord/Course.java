package ru.itche.petproject.frontendservice.course.entityRecord;

import java.time.LocalDate;

public record Course(Integer id,
                     String title,
                     String titleCurriculum,
                     LocalDate updateDate) {
}
