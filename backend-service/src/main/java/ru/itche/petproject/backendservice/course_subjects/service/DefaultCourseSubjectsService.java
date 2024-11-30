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
    public Iterable<CourseSubjects> getAllCourseSubjects() {
        return this.courseSubjectsRepository.findAll();
    }

    @Override
    public Map<String, List<Subject>> getSubjectsByCourse(Integer courseId) {
        List<Object[]> rawResults = courseSubjectsRepository.findSubjectsByCourseId(courseId);

        Map<String, List<Subject>> result = new LinkedHashMap<>();
        if (!rawResults.isEmpty()) {
            String courseTitle = (String) rawResults.get(0)[0]; // Название курса (одно для всех записей)
            List<Subject> subjects = rawResults.stream()
                    .map(row -> new Subject((Integer) row[1], (String) row[2], null, null)) // Создаем объекты Subject
                    .toList();

            result.put(courseTitle, subjects);
        }
        return result;
    }

    @Override
    public Optional<CourseSubjects> findCourseSubjects(int courSubId) {
        return this.courseSubjectsRepository.findById(courSubId);
    }

    @Override
    @Transactional
    public void setAllSubjectsToCourse(Integer courseId, List<Integer> subjectIds) {

       Course course = courseRepository.findById(courseId).orElseThrow();
        Iterable<Subject> subjects = subjectRepository.findAllById(subjectIds);

        List<CourseSubjects> courseSubjectsList = StreamSupport.stream(subjects.spliterator(), false)
                .map(subject -> new CourseSubjects(null, course, subject, null))
                .toList();

        this.courseSubjectsRepository.saveAll(courseSubjectsList);
    }



    @Override
    @Transactional
    public void updateCourseSubjects(int courSubId, String details) {
        this.courseSubjectsRepository.findById(courSubId)
                .ifPresent(courSubject -> {
                    courSubject.setDetails(details);
                });
    }

    @Override
    @Transactional
    public void deleteCourseSubjects(int courSubId) {
        this.courseSubjectsRepository.deleteById(courSubId);
    }
}
