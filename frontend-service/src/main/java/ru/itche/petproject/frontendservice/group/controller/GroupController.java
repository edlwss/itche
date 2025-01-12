package ru.itche.petproject.frontendservice.group.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itche.petproject.frontendservice.group.client.GroupsRestClient;
import ru.itche.petproject.frontendservice.student.entityRecord.Student;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("musical-school/groups")
public class GroupController {

    private final GroupsRestClient groupsRestClient;
    private  final HttpSession session;

    @ModelAttribute("role")
    public String getRole() {
        return this.session.getAttribute("role").toString();
    }
    @GetMapping
    public String getGroups(Model model) {
        model.addAttribute("groups", groupsRestClient.findAllGroups());
        return "group/list";
    }

    @GetMapping("/group-students/{groupId:\\d+}")
    public String getGroupAtStudents(@PathVariable("groupId") int groupId, Model model) {
        model.addAttribute("group_student", groupsRestClient.findStudentsByGroups(groupId));
        return "group/student_list";
    }
}
