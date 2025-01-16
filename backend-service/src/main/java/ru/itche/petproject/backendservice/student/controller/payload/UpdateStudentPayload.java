package ru.itche.petproject.backendservice.student.controller.payload;

import ru.itche.petproject.backendservice.user.controller.payload.UpdateUserPayload;

public record UpdateStudentPayload (Integer group,
                                    String details,
                                    UpdateUserPayload userPayload) {
}
