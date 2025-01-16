package ru.itche.petproject.frontendservice.lesson.client;

import ru.itche.petproject.frontendservice.lesson.entityRecord.Lesson;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface LessonRestClient {

    Map<LocalDate, List<Lesson>> getGroupSchedule(Integer groupId, int year, int month);
    void createLesson(Integer group, Integer subject, LocalTime timeLesson,
                             LocalDate dateLesson);
    void deleteLesson(Integer lessonId);
    byte[] getGradePdf(Integer groupId, int year, int month);
}
