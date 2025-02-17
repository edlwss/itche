package ru.itche.petproject.backendservice.group.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itche.petproject.backendservice.course.entity.Course;
import ru.itche.petproject.backendservice.course.repository.CourseRepository;
import ru.itche.petproject.backendservice.group.entity.Group;
import ru.itche.petproject.backendservice.group.repository.GroupRepository;
import ru.itche.petproject.backendservice.student.entity.Student;
import ru.itche.petproject.backendservice.student.repository.StudentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultGroupService implements GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public Map<String, List<Student>> getStudentsByGroup(Integer groupId) {
        List<Object[]> rawResults = groupRepository.findGroupWithStudents(groupId);

        Map<String, List<Student>> result = new LinkedHashMap<>();
        if (!rawResults.isEmpty()) {
            // Извлекаем название группы из первого элемента результата
            String groupTitle = (String) rawResults.get(0)[0];

            // Сбор студентов
            List<Student> students = rawResults.stream()
                    .map(row -> row[1] != null ? studentRepository.findById((Integer) row[1]).orElse(null) : null)
                    .filter(Objects::nonNull)
                    .toList();

            // Если нет студентов, добавляем группу с пустым списком
            if (students.isEmpty()) {
                result.put(groupTitle, new ArrayList<>());  // Пустой список студентов
            } else {
                result.put(groupTitle, students);
            }
        } else {
            // В случае, если группа без студентов
            result.put("Группа не найдена", new ArrayList<>());
        }
        return result;
    }

    @Override
    @Transactional
    public Map<String, List<Group>> getGroups() {
        List<Object[]> rawResults = groupRepository.findGroupsGroupedByCourse();

        Map<String, List<Group>> groupedResults = new LinkedHashMap<>();
        for (Object[] row : rawResults) {
            String courseTitle = (String) row[0];
            Integer groupId = (Integer) row[1];

            Group group = groupRepository.findById(groupId).orElse(null);
            if (group != null) {
                groupedResults.computeIfAbsent(courseTitle, k -> new ArrayList<>()).add(group);
            }
        }

        return groupedResults;
    }

    @Override
    public Optional<Group> getGroup(Integer groupId) {
        return this.groupRepository.findById(groupId);
    }

    @Override
    public Iterable<Group> getListGroups() {
        return groupRepository.findAll();
    }

    @Override
    @Transactional
    public void createGroup(String title, Integer course,
                            LocalDate localDate, LocalDate localDate1) {

        Course courseObj = courseRepository.findById(course).orElse(null);

        this.groupRepository.save(new Group(null, title, courseObj, localDate, localDate1));
    }

}
