package ru.itche.petproject.frontendservice.teacher.entityRecord;

import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;

public record TeacherSubjects(Integer id,
                              Subject subject,
                              Teacher teacher,
                              String details) {
}
