package ru.itche.petproject.backendservice.course_subjects.controller.payload;

import java.util.List;

public record NewCourseSubjectsPayload(Integer courseId, List<Integer> subjects) {
}
