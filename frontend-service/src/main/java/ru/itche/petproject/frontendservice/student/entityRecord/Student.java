package ru.itche.petproject.frontendservice.student.entityRecord;

import ru.itche.petproject.frontendservice.group.entityRecord.Group;
import ru.itche.petproject.frontendservice.user.entityRecord.User;

public record Student(Integer id,
                      User user,
                      Group group,
                      String details) {
}
