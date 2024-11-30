package ru.itche.petproject.frontendservice.subject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itche.petproject.frontendservice.course.client.CourseRestClient;
import ru.itche.petproject.frontendservice.course.controller.payload.UpdateCoursePayload;
import ru.itche.petproject.frontendservice.course.entityRecord.Course;
import ru.itche.petproject.frontendservice.subject.client.SubjectRestClient;
import ru.itche.petproject.frontendservice.subject.controller.payload.UpdateSubjectPayload;
import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;

@Controller
@RequiredArgsConstructor
@RequestMapping("musical-school/subjects/subject/{subjectId:\\d+}")
public class SubjectController {

    private final SubjectRestClient subjectRestClient;

    //выносим повторящийся код для страницы отображения курса и страницы редактирования курса
    @ModelAttribute("subject")
    public Subject subject(@PathVariable("subjectId") Integer subjectId) {
        return this.subjectRestClient.findSubject(subjectId).orElseThrow();
    }

    @GetMapping()
    public String getSubject() {
        return "subject/detail";
    }

    @GetMapping("edit")
    public String getSubjectEditPage() {
        return "subject/edit";
    }

    @PostMapping("edit")
    public String updateSubject(@ModelAttribute("subject") Subject subject, UpdateSubjectPayload payload) {
        this.subjectRestClient.updateSubject(subject.id(), payload.title(), payload.titleSyllabus());
        return "redirect:/musical-school/subjects/subject/%d".formatted(subject.id());
    }

    @PostMapping("delete")
    public String deleteSubject(@ModelAttribute("subject") Subject subject) {
        this.subjectRestClient.deleteSubject(subject.id());
        return "redirect:/musical-school/subjects/list";
    }
}
