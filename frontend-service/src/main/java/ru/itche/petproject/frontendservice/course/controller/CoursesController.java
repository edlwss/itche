package ru.itche.petproject.frontendservice.course.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itche.petproject.frontendservice.course.client.CourseRestClient;
import ru.itche.petproject.frontendservice.course.controller.payload.NewCoursePayload;


@Controller
@RequiredArgsConstructor
@RequestMapping("musical-school/courses")
public class CoursesController {

    private final CourseRestClient courseRestClient;
    private  final HttpSession session;

    @ModelAttribute("role")
    public String getRole() {
        return this.session.getAttribute("role").toString();
    }

    @GetMapping("/list")
    public String findAllCourses(Model model) {
        model.addAttribute("courses", this.courseRestClient.findAllCourses());
        return "course/list";
    }

    @GetMapping("create")
    public String createCoursePage() {
        return "course/create";
    }

    @PostMapping("create")
    public String createCourse(NewCoursePayload payload) {
        this.courseRestClient.createCourse(payload.title(), payload.titleCurriculum());
        return "redirect:/musical-school/courses/list";
    }
}
