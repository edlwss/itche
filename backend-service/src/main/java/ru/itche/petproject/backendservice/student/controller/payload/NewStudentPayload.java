package ru.itche.petproject.backendservice.student.controller.payload;

import ru.itche.petproject.backendservice.user.controller.payload.NewUserPayload;

public record NewStudentPayload (Integer id,
                                 NewUserPayload userPayload,
                                 String details) {
}
