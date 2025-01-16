package ru.itche.petproject.frontendservice.subject.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itche.petproject.frontendservice.subject.client.SubjectRestClient;
import ru.itche.petproject.frontendservice.subject.controller.payload.UpdateSubjectPayload;
import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;
import ru.itche.petproject.frontendservice.teacher.client.TeacherRestClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("musical-school/subjects/subject/{subjectId:\\d+}")
public class SubjectController {

    private final SubjectRestClient subjectRestClient;
    private final TeacherRestClient teacherRestClient;
    private  final HttpSession session;

    @ModelAttribute("role")
    public String getRole() {
        return this.session.getAttribute("role").toString();
    }

    @ModelAttribute("subject")
    public Subject subject(@PathVariable("subjectId") Integer subjectId) {
        return this.subjectRestClient.findSubject(subjectId).orElseThrow();
    }

    @GetMapping("edit")
    public String getSubjectEditPage(Model model) {
        model.addAttribute("teachers", teacherRestClient.getAllTeachers());
        return "subject/edit";
    }

    @PostMapping("edit")
    public String updateSubject(@ModelAttribute("subject") Subject subject, UpdateSubjectPayload payload) {
        this.subjectRestClient.updateSubject(subject.id(), payload.title(), payload.titleSyllabus(),
                payload.teacherId());
        return "redirect:/musical-school/courses/list";
    }

    @PostMapping("delete")
    public String deleteSubject(@ModelAttribute("subject") Subject subject) {
        this.subjectRestClient.deleteSubject(subject.id());
        return "redirect:/musical-school/courses/list";
    }
}
