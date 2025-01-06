package ru.itche.petproject.frontendservice.teacher.controller.payload;

import ru.itche.petproject.frontendservice.user.controller.payload.UpdateUserPayload;

public record UpdateTeacherPayload(UpdateUserPayload userPayload,
                                   String education,
                                   String details) {
}
