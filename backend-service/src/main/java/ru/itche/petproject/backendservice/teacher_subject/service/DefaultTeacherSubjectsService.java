package ru.itche.petproject.backendservice.teacher_subject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itche.petproject.backendservice.course.entity.Course;
import ru.itche.petproject.backendservice.subject.entity.Subject;
import ru.itche.petproject.backendservice.subject.repository.SubjectRepository;
import ru.itche.petproject.backendservice.teacher_subject.entity.TeacherSubjects;
import ru.itche.petproject.backendservice.teacher_subject.repository.TeacherSubjectsRepository;
import ru.itche.petproject.backendservice.teacher.entity.Teacher;
import ru.itche.petproject.backendservice.teacher.repository.TeacherRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DefaultTeacherSubjectsService implements TeacherSubjectsService {

    private final TeacherSubjectsRepository teacherSubjectsRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public Map<String, List<Subject>> getCoursesByTeacher(Integer teacherId) {
        List<Object[]> rawResults = teacherSubjectsRepository.findCoursesByTeacherId(teacherId);

        Map<String, List<Subject>> result = new LinkedHashMap<>();
        if (!rawResults.isEmpty()) {
            String teacherFullName = (String) rawResults.get(0)[0];

            List<Subject> subjects = rawResults.stream()
                    .map(row -> new Subject((Integer) row[1], (String) row[2], null, null))
                    .toList();
            result.put(teacherFullName, subjects);
        }
        return result;
    }

    @Override
    @Transactional
    public void addCoursesToCourse(Integer teacherId, List<Integer> coursesIds) {
        // Получаем курс из базы данных
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Учитель с id " + teacherId + " не найден"));

        // Проходимся по всем ID предметов и создаем записи CourseSubjects
        List<TeacherSubjects> teacherSubjects = coursesIds.stream()
                .map(courseId -> {
                    Subject subject = subjectRepository.findById(courseId)
                            .orElseThrow(() -> new IllegalArgumentException("Курс с id " + courseId + " не найден"));

                    return new TeacherSubjects(null, subject, teacher, null);
                })
                .toList();

        // Сохраняем все записи в базу
        teacherSubjectsRepository.saveAll(teacherSubjects);
    }
}
