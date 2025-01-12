package ru.itche.petproject.backendservice.teacher.controller.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.itche.petproject.backendservice.user.controller.payload.UpdateUserPayload;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateTeacherPayload(UpdateUserPayload userPayload,
                                   String education,
                                   String details) {
}
