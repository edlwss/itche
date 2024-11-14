package ru.itche.petproject.frontendservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itche.petproject.frontendservice.entity.Course;
import ru.itche.petproject.frontendservice.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultCourseService implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public List<Course> getAllCourses() {
        return this.courseRepository.findAll();
    }

    @Override
    public Course createCourse(String title, String titleCurriculum) {
        return this.courseRepository.save(new Course(null, title, titleCurriculum));
    }

    @Override
    public Optional<Course> findCourse(Integer courseId) {
        return this.courseRepository.findById(courseId);
    }

    @Override
    public void updateCourse(Integer id, String title, String titleCurriculum) {
        this.courseRepository.findById(id)
                .ifPresent(course -> {
                    course.setTitle(title);
                    course.setTitleCurriculum(titleCurriculum);
                });
    }

    @Override
    public void deteleCourse(Integer id) {
        this.courseRepository.deleteById(id);
    }
}
