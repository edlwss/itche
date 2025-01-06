package ru.itche.petproject.backendservice.group.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itche.petproject.backendservice.group.entity.Group;
import ru.itche.petproject.backendservice.group.repository.GroupRepository;
import ru.itche.petproject.backendservice.group.service.GroupService;
import ru.itche.petproject.backendservice.student.entity.Student;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("musical-school-api/groups")
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public Iterable<Group> getGroups() {
        return this.groupService.getGroups();
    }

    @GetMapping("/group/{groupId:\\d+}")
    public Group getGroup(@PathVariable Integer groupId) {
        return this.groupService.getGroup(groupId).orElse(null);
    }

    @GetMapping("/group-students/{groupId:\\d+}")
    public Map<String, List<Student>> getGroupStudents(@PathVariable Integer groupId) {
        return this.groupService.getStudentsByGroup(groupId);
    }

}
