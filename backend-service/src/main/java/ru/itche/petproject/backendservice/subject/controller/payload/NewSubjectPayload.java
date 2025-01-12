package ru.itche.petproject.backendservice.subject.controller.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record NewSubjectPayload(String title, String titleSyllabus, Integer teacherId) {
}
