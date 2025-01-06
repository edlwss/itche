package ru.itche.petproject.backendservice.teacher_subject.controller.payload;

import ru.itche.petproject.backendservice.course.entity.Course;
import ru.itche.petproject.backendservice.teacher.entity.Teacher;

public record NewTeacherSubjectsPayload(Course course,
                                        Teacher teacher,
                                        String details) {
}
