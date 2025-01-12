package ru.itche.petproject.frontendservice.grade.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itche.petproject.frontendservice.grade.client.GradeRestClient;
import ru.itche.petproject.frontendservice.grade.entityRecord.Grade;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/musical-school/grade")
public class GradeController {

    private final GradeRestClient gradeRestClient;
    private  final HttpSession session;

    @ModelAttribute("role")
    public String getRole() {
        return this.session.getAttribute("role").toString();
    }

    @GetMapping
    public String gradeByGroup(Model model,
                               @RequestParam Integer groupId,
                               @RequestParam Integer lessonId) {
        model.addAttribute("groupId", groupId);
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("grades", gradeRestClient.getGradesByGroup(groupId, lessonId));

        System.out.println(gradeRestClient.getGradesByGroup(groupId, lessonId));

        return "grade/grade";
    }

    @PostMapping("add")
    public String submitGrades(@RequestParam Integer groupId, @RequestParam Integer lessonId,
                               @RequestBody Map<String, Map<String, String>> data) {

        System.out.println(data);

        this.gradeRestClient.createGrades(groupId, lessonId, data);

        return "redirect:/musical-school/lessons?groupId=" + groupId + "&year=2025&month=1";
    }

}
