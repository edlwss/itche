package ru.itche.petproject.frontendservice.repository;

import org.springframework.stereotype.Repository;
import ru.itche.petproject.frontendservice.entity.Course;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@Repository
public class InMemoryCourseRepository implements CourseRepository {

    private final List<Course> courses = Collections.synchronizedList(new LinkedList<>());

    @Override
    public List<Course> findAll() {
        return Collections.unmodifiableList(courses);
    }

    @Override
    public Course save(Course course) {
        course.setId(this.courses.stream()
                .max(Comparator.comparingInt(Course::getId))
                .map(Course::getId)
                .orElse(0) + 1);
        this.courses.add(course);
        return course;
    }

    @Override
    public Optional<Course> findById(Integer courseId) {
        return this.courses.stream()
                .filter(product -> Objects.equals(courseId, product.getId()))
                .findFirst();
    }

    @Override
    public void deleteById(Integer id) {
        this.courses.removeIf(course -> Objects.equals(id, course.getId()));
    }
}
