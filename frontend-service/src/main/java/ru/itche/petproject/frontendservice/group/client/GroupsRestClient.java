package ru.itche.petproject.frontendservice.group.client;

import ru.itche.petproject.frontendservice.group.entityRecord.Group;
import ru.itche.petproject.frontendservice.student.entityRecord.Student;

import java.util.List;
import java.util.Map;

public interface GroupsRestClient {

    Map<String, List<Group>> findAllGroups();
    Map<String, List<Student>> findStudentsByGroups(Integer groupId);
    List<Group> findListGroups();
}
