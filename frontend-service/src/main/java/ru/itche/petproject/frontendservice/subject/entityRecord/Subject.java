package ru.itche.petproject.frontendservice.subject.entityRecord;

import org.springframework.cglib.core.Local;
import ru.itche.petproject.frontendservice.teacher.entityRecord.Teacher;

import java.time.LocalDate;

public record Subject(Integer id, String title, String titleSyllabus,
                      LocalDate updateDate, Teacher teacher) {}
