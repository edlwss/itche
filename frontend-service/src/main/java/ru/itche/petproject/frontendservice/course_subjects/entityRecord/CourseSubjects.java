package ru.itche.petproject.frontendservice.course_subjects.entityRecord;

import ru.itche.petproject.frontendservice.course.entityRecord.Course;
import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;

public record CourseSubjects(Integer id, Course course, Subject subject, String details) {
}
