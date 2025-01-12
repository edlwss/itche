package ru.itche.petproject.frontendservice.grade.entityRecord;

import ru.itche.petproject.frontendservice.lesson.entityRecord.Lesson;
import ru.itche.petproject.frontendservice.student.entityRecord.Student;

public record Grade (Integer id,
                     Student student,
                     Lesson lesson,
                     Integer estimation,
                     String presence){
}
