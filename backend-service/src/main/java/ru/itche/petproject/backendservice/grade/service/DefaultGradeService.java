package ru.itche.petproject.backendservice.grade.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itche.petproject.backendservice.grade.entity.Grade;
import ru.itche.petproject.backendservice.grade.repository.GradeRepository;
import ru.itche.petproject.backendservice.group.service.GroupService;
import ru.itche.petproject.backendservice.lesson.entity.Lesson;
import ru.itche.petproject.backendservice.lesson.service.LessonService;
import ru.itche.petproject.backendservice.student.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DefaultGradeService implements GradeService {

    private final GradeRepository gradeRepository;
    private final GroupService groupService;
    private final LessonService lessonService;

    @Override
    @Transactional
    public Iterable<Grade> getGradeByGroup(Integer groupId, Integer lessonId) {
        Map<String, List<Student>> groupStudentsMap = groupService.getStudentsByGroup(groupId);
        List<Student> students = groupStudentsMap.getOrDefault(
                groupService.getGroup(groupId).orElseThrow(() -> new IllegalArgumentException("Invalid group ID")).getTitle(),
                new ArrayList<>()
        );

        Lesson lesson = lessonService.findLessonId(lessonId);

        List<Grade> existingGrades = (List<Grade>) gradeRepository.findGradesByGroupAndSubject(groupId, lessonId);

        Map<Integer, Grade> gradeMap = existingGrades.stream()
                .collect(Collectors.toMap(grade -> grade.getStudent().getId(), grade -> grade));

        List<Grade> grades = new ArrayList<>();
        for (Student student : students) {
            Grade grade = gradeMap.getOrDefault(student.getId(), createEmptyGrade(student, lesson));
            grades.add(grade);
        }

        return grades;
    }

    private Grade createEmptyGrade(Student student,  Lesson lesson) {
        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setLesson(lesson);
        grade.setEstimation(null);
        grade.setPresence(null);
        return grade;
    }

    @Override
    @Transactional
    public Iterable<Grade> createGrades(Integer groupId, Integer lessonId,
                                        Map<String, String> estimation,
                                        Map<String, String> presence) {
        Map<String, List<Student>> groupStudentsMap = groupService.getStudentsByGroup(groupId);
        List<Student> students = groupStudentsMap.getOrDefault(
                groupService.getGroup(groupId).orElseThrow(() -> new IllegalArgumentException("Invalid group ID")).getTitle(),
                new ArrayList<>()
        );

        List<Grade> grades = new ArrayList<>();
        Lesson lesson = lessonService.findLessonId(lessonId);

        for (Student student : students) {
            String studentId = String.valueOf(student.getId());

            String estimationValue = estimation.get(studentId);
            String presenceValue = presence.get(studentId);

            Optional<Grade> existingGradeOpt = gradeRepository.findByStudentAndLesson(student.getId(), lesson.getId());

            Grade grade;
            if (existingGradeOpt.isPresent()) {
                grade = existingGradeOpt.get();
            } else {
                grade = new Grade();
                grade.setStudent(student);
                grade.setLesson(lesson);
            }

            grade.setEstimation(estimationValue != null && !estimationValue.equals("null") ? Integer.valueOf(estimationValue) : null);
            grade.setPresence(presenceValue != null && !presenceValue.equals("null") ? presenceValue : null);

            grades.add(grade);
        }

        return gradeRepository.saveAll(grades);
    }

    @Override
    @Transactional
    public Map<String, Double> getGradeByStudentId(Integer studentId) {
        List<Object[]> result = gradeRepository.findSubjectsAndGradesByStudentId(studentId);

        return result.stream()
                .collect(Collectors.groupingBy(
                        row -> (String) row[0],
                        Collectors.averagingDouble(row -> ((Number) row[1]).doubleValue())
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Math.round(entry.getValue() * 10.0) / 10.0
                ));
    }
}

