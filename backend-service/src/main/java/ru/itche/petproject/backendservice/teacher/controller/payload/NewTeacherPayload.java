package ru.itche.petproject.backendservice.teacher.controller.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.itche.petproject.backendservice.user.controller.payload.NewUserPayload;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record NewTeacherPayload (NewUserPayload userPayload,
                                 String education,
                                 String details) {
}
