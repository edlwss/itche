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

import java.util.List;
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
