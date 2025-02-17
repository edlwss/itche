package ru.itche.petproject.backendservice.group.service;

import ru.itche.petproject.backendservice.group.entity.Group;
import ru.itche.petproject.backendservice.student.entity.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GroupService {

    Map<String, List<Student>> getStudentsByGroup(Integer groupId);

    Map<String, List<Group>> getGroups();

    Optional<Group> getGroup(Integer groupId);

    Iterable<Group> getListGroups();

    void createGroup(String title, Integer course, LocalDate localDate, LocalDate localDate1);
}
