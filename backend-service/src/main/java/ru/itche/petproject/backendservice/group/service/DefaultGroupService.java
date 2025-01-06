package ru.itche.petproject.backendservice.group.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itche.petproject.backendservice.group.entity.Group;
import ru.itche.petproject.backendservice.group.repository.GroupRepository;
import ru.itche.petproject.backendservice.student.entity.Student;
import ru.itche.petproject.backendservice.student.repository.StudentRepository;
import ru.itche.petproject.backendservice.subject.entity.Subject;

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

    @Override
    public Map<String, List<Student>> getStudentsByGroup(Integer groupId) {
        List<Object[]> rawResults = groupRepository.findGroupWithStudents(groupId);

        Map<String, List<Student>> result = new LinkedHashMap<>();
        if (!rawResults.isEmpty()) {
            String groupTitle = (String) rawResults.get(0)[0];
            List<Student> students = rawResults.stream()
                    .map(row -> studentRepository.findById((Integer) row[1])
                            .orElse(null))
                    .filter(Objects::nonNull)
                    .toList();
            result.put(groupTitle, students);
        }
        return result;
    }

    @Override
    public Iterable<Group> getGroups() {
        return this.groupRepository.findAll();
    }

    @Override
    public Optional<Group> getGroup(Integer groupId) {
        return this.groupRepository.findById(groupId);
    }

}
