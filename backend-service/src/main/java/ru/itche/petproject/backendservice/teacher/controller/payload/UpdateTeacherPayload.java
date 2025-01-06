package ru.itche.petproject.backendservice.teacher.controller.payload;

import ru.itche.petproject.backendservice.user.controller.payload.UpdateUserPayload;

public record UpdateTeacherPayload(UpdateUserPayload userPayload,
                                   String education,
                                   String details) {
}
