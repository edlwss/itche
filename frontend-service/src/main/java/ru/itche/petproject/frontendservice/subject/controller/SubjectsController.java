package ru.itche.petproject.frontendservice.subject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itche.petproject.frontendservice.course.controller.payload.NewCoursePayload;
import ru.itche.petproject.frontendservice.subject.client.SubjectRestClient;
import ru.itche.petproject.frontendservice.subject.controller.payload.NewSubjectPayload;


@Controller
@RequiredArgsConstructor
@RequestMapping("musical-school/subjects")
public class SubjectsController {

    private final SubjectRestClient subjectRestClient;

    @GetMapping("list")
    public String getSubjectsList(Model model) {
        model.addAttribute("subjects", this.subjectRestClient.getAllSubjects());
        return "subject/list";
    }

    @GetMapping("create")
    public String createSubjectPage() {
        return "subject/create";
    }

    @PostMapping("create")
    public String createSubject(NewSubjectPayload payload) {
        this.subjectRestClient.createSubject(payload.title(), payload.titleSyllabus());
        return "redirect:/musical-school/subjects/list";
    }
}
