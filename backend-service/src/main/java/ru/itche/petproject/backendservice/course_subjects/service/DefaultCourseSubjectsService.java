package ru.itche.petproject.backendservice.course_subjects.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itche.petproject.backendservice.course.repository.CourseRepository;
import ru.itche.petproject.backendservice.course_subjects.entity.CourseSubjects;
import ru.itche.petproject.backendservice.course_subjects.repository.CourseSubjectsRepository;
import ru.itche.petproject.backendservice.subject.entity.Subject;
import ru.itche.petproject.backendservice.course.entity.Course;
import ru.itche.petproject.backendservice.subject.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DefaultCourseSubjectsService implements CourseSubjectsService {

    private final CourseSubjectsRepository courseSubjectsRepository;
    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public Map<String, List<Subject>> getSubjectsByCourse(Integer courseId) {
        List<Object[]> rawResults = courseSubjectsRepository.findSubjectsByCourseId(courseId);

        Map<String, List<Subject>> result = new LinkedHashMap<>();
        if (!rawResults.isEmpty()) {
            String courseTitle = (String) rawResults.get(0)[0];
            List<Subject> subjects = rawResults.stream()
                    .map(row -> new Subject((Integer) row[1], (String) row[2], null, null, null))
                    .toList();
            result.put(courseTitle, subjects);
        }
        return result;
    }

    @Override
    @Transactional
    public void addSubjectsToCourse(Integer courseId, List<Integer> subjectsIds) {
        // Получаем курс из базы данных
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Курс с id " + courseId + " не найден"));

        // Проходимся по всем ID предметов и создаем записи CourseSubjects
        List<CourseSubjects> courseSubjects = subjectsIds.stream()
                .map(subjectId -> {
                    Subject subject = subjectRepository.findById(subjectId)
                            .orElseThrow(() -> new IllegalArgumentException("Предмет с id " + subjectId + " не найден"));

                    return new CourseSubjects(null, course, subject, null); // details = null
                })
                .toList();

        // Сохраняем все записи в базу
        courseSubjectsRepository.saveAll(courseSubjects);
    }

}
