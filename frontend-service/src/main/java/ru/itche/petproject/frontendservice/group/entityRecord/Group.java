package ru.itche.petproject.frontendservice.group.entityRecord;

import ru.itche.petproject.frontendservice.course.entityRecord.Course;

import java.util.Date;

public record Group(Integer id,
                    String title,
                    Course course,
                    Date startEducation,
                    Date endEducation) {
}
