package ru.itche.petproject.frontendservice.subject.controller.payload;

public record NewSubjectPayload(String title, String titleSyllabus, Integer teacherId) {
}
