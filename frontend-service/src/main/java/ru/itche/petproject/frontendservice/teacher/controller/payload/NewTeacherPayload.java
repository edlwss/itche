package ru.itche.petproject.frontendservice.teacher.controller.payload;

import ru.itche.petproject.frontendservice.user.controller.payload.NewUserPayload;

public record NewTeacherPayload (NewUserPayload userPayload,
                                 String education,
                                 String details) {
}
