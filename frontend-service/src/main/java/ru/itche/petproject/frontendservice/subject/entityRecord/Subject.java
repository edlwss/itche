package ru.itche.petproject.frontendservice.subject.entityRecord;

import ru.itche.petproject.frontendservice.teacher.entityRecord.Teacher;

public record Subject(Integer id, String title, String titleSyllabus, Teacher teacher) {}
