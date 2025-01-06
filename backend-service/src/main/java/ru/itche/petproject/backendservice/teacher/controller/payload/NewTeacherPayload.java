package ru.itche.petproject.backendservice.teacher.controller.payload;

import ru.itche.petproject.backendservice.user.controller.payload.NewUserPayload;

public record NewTeacherPayload (NewUserPayload userPayload,
                                 String education,
                                 String details) {
}
