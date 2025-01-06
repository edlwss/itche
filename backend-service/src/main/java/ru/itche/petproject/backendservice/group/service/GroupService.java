package ru.itche.petproject.backendservice.group.service;

import ru.itche.petproject.backendservice.group.entity.Group;
import ru.itche.petproject.backendservice.student.entity.Student;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GroupService {

    Map<String, List<Student>> getStudentsByGroup(Integer groupId);

    Iterable<Group> getGroups();

    Optional<Group> getGroup(Integer groupId);

}
