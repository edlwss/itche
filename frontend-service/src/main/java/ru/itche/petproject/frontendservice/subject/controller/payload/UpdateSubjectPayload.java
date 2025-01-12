package ru.itche.petproject.frontendservice.subject.controller.payload;

public record UpdateSubjectPayload(String title, String titleSyllabus, Integer teacherId) {
}
