package ru.itche.petproject.frontendservice.subject.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itche.petproject.frontendservice.course.controller.payload.NewCoursePayload;
import ru.itche.petproject.frontendservice.subject.client.SubjectRestClient;
import ru.itche.petproject.frontendservice.subject.controller.payload.NewSubjectPayload;
import ru.itche.petproject.frontendservice.teacher.client.TeacherRestClient;


@Controller
@RequiredArgsConstructor
@RequestMapping("musical-school/subjects")
public class SubjectsController {

    private final SubjectRestClient subjectRestClient;
    private final TeacherRestClient teacherRestClient;
    private  final HttpSession session;

    @ModelAttribute("role")
    public String getRole() {
        return this.session.getAttribute("role").toString();
    }

    @GetMapping("create")
    public String createSubjectPage(Model model) {
        model.addAttribute("teachers", teacherRestClient.getAllTeachers());
        return "subject/create";
    }

    @PostMapping("create")
    public String createSubject(NewSubjectPayload payload) {
        this.subjectRestClient.createSubject(payload.title(), payload.titleSyllabus(),
                payload.teacherId());
        return "redirect:/musical-school/subjects/list";
    }
}
