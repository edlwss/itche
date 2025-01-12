package ru.itche.petproject.backendservice.lesson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itche.petproject.backendservice.course.entity.Course;
import ru.itche.petproject.backendservice.group.entity.Group;
import ru.itche.petproject.backendservice.group.repository.GroupRepository;
import ru.itche.petproject.backendservice.lesson.entity.Lesson;
import ru.itche.petproject.backendservice.lesson.repository.LessonRepository;
import ru.itche.petproject.backendservice.subject.entity.Subject;
import ru.itche.petproject.backendservice.subject.repository.SubjectRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TreeMap;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultLessonService implements LessonService {

    private final LessonRepository lessonRepository;
    private final SubjectRepository subjectRepository;
    private final GroupRepository groupRepository;

    @Override
    @Transactional
    public Lesson createLesson(Integer groupId, Integer subjectId, LocalTime timeLesson,
                               LocalDate dateLesson) {

        Group group = groupRepository.findById(groupId).orElse(null);
        Subject subject = subjectRepository.findById(subjectId).orElse(null);

        return this.lessonRepository.save(new Lesson(null, group, subject, timeLesson, dateLesson));
    }

    @Override
    @Transactional
    public void updateLesson(Integer id, Integer groupId, Integer subjectId, LocalTime timeLesson,
                               LocalDate dateLesson) {

        Group group = groupRepository.findById(groupId).orElse(null);
        Subject subject = subjectRepository.findById(subjectId).orElse(null);

        this.lessonRepository.findById(id)
                .ifPresent(lesson -> {
                    lesson.setGroup(group);
                    lesson.setSubject(subject);
                    lesson.setTimeLesson(timeLesson);
                    lesson.setDateLesson(dateLesson);}
                );
    }

    public Map<LocalDate, List<Lesson>> getGroupScheduleForMonth(Integer groupId, int year, int month) {
        List<Lesson> lessons = lessonRepository.findLessonsByGroupAndMonth(groupId, month, year);

        return lessons.stream()
                .collect(Collectors.groupingBy(Lesson::getDateLesson));
    }

    @Override
    @Transactional
    public Map<LocalDate, List<Lesson>> getCalendarFormattedSchedule(Integer groupId, int year, int month) {
        // Получаем список уроков за указанный месяц
        List<Lesson> lessons = lessonRepository.findLessonsByGroupAndMonth(groupId, month, year);

        // Группируем уроки по дате
        Map<LocalDate, List<Lesson>> groupedLessons = lessons.stream()
                .collect(Collectors.groupingBy(Lesson::getDateLesson, TreeMap::new, Collectors.toList()));

        // Заполняем пропущенные даты пустыми списками
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            groupedLessons.putIfAbsent(date, Collections.emptyList());
        }

        return groupedLessons;
    }

    @Override
    public Lesson findLessonId(Integer lessonId) {
        return lessonRepository.findById(lessonId).orElse(null);
    }


    @Override
    @Transactional
    public void deleteLesson(Integer id) {
        lessonRepository.deleteById(id);
    }
}
