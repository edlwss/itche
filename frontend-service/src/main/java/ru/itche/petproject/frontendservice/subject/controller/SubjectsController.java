package ru.itche.petproject.frontendservice.subject.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "redirect:/musical-school/courses/list";
    }

    @GetMapping("/teachers/pdf")
    public ResponseEntity<byte[]> downloadGradePdf() {

        byte[] pdfContent = subjectRestClient.getGradePdfBySubject();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=subjects_teachers_" + ".pdf");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
}
