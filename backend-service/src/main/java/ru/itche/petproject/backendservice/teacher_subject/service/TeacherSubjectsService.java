package ru.itche.petproject.backendservice.teacher_subject.service;

import ru.itche.petproject.backendservice.course.entity.Course;
import ru.itche.petproject.backendservice.subject.entity.Subject;

import java.util.List;
import java.util.Map;

public interface TeacherSubjectsService {
    Map<String, List<Subject>> getCoursesByTeacher(Integer teacherId);

    void addCoursesToCourse(Integer teacherId, List<Integer> coursesIds);
}
