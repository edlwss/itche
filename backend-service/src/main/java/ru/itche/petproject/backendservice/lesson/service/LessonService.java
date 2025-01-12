package ru.itche.petproject.backendservice.lesson.service;

import ru.itche.petproject.backendservice.lesson.entity.Lesson;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface LessonService {

    Lesson createLesson(Integer group, Integer subject, LocalTime localTime, LocalDate localDate);

    void updateLesson(Integer id, Integer groupId, Integer subjectId, LocalTime timeLesson,
                      LocalDate dateLesson);

    void deleteLesson(Integer id);

    Map<LocalDate, List<Lesson>> getCalendarFormattedSchedule(Integer groupId, int year, int month);

    Lesson findLessonId(Integer lessonId);
}
