package ru.itche.petproject.frontendservice.student.controller.payload;

import ru.itche.petproject.frontendservice.user.controller.payload.UpdateUserPayload;

public record UpdateStudentPayload(Integer group,
                                   String details,
                                   UpdateUserPayload updateUserPayload) {
}
