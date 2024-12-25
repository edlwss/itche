package ru.itche.petproject.backendservice.student.controller.payload;

import ru.itche.petproject.backendservice.group.entity.Group;
import ru.itche.petproject.backendservice.user.entity.User;

public record UpdateStudentPayload (Integer group, String details){
}
