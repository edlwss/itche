package ru.itche.petproject.backendservice.course.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itche.petproject.backendservice.course.entity.Course;
import ru.itche.petproject.backendservice.course.repository.CourseRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultCourseService implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public Iterable<Course> getAllCourses() {
        return this.courseRepository.findAll();
    }

    @Override
    public Optional<Course> findCourse(Integer courseId) {
        return this.courseRepository.findById(courseId);
    }

    @Override
    @Transactional
    public Course createCourse(String title, String titleCurriculum) {
        return this.courseRepository.save(new Course(null, title, titleCurriculum));
    }

    @Override
    @Transactional
    public void updateCourse(Integer id, String title, String titleCurriculum) {
        this.courseRepository.findById(id)
                .ifPresent(course -> {
                    course.setTitle(title);
                    course.setTitleCurriculum(titleCurriculum);
                });
    }

    @Override
    @Transactional
    public void deleteCourse(Integer id) {
        this.courseRepository.deleteById(id);
    }
}
