package ru.itche.petproject.frontendservice.teacher.entityRecord;

import ru.itche.petproject.frontendservice.user.entityRecord.User;

public record Teacher(Integer id,
                      User user,
                      String education,
                      String details) {
}
