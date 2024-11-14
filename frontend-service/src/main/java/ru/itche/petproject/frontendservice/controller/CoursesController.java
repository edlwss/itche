package ru.itche.petproject.frontendservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itche.petproject.frontendservice.controller.payload.NewCoursePayload;
import ru.itche.petproject.frontendservice.entity.Course;
import ru.itche.petproject.frontendservice.service.CourseService;

@Controller
@RequiredArgsConstructor
@RequestMapping("musical-school/courses")
public class CoursesController {

    private final CourseService courseService;

    @GetMapping("list")
    public String getCoursesList(Model model) {
        model.addAttribute("courses", this.courseService.getAllCourses());
        return "course/list";
    }

    @GetMapping("create")
    public String createCoursePage(Model model) {
        return "course/create";
    }

    @PostMapping("create")
    public String createCourse(NewCoursePayload payload) {
        Course course = this.courseService.createCourse(payload.title(), payload.titleCurriculum());
        return "redirect:/musical-school/courses/list";
    }
}
