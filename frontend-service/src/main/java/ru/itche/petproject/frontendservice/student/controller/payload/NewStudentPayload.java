package ru.itche.petproject.frontendservice.student.controller.payload;


import ru.itche.petproject.frontendservice.user.controller.payload.NewUserPayload;

public record NewStudentPayload(NewUserPayload userPayload,
                                String details) {
}
