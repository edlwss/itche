package ru.itche.petproject.frontendservice.course_subjects.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itche.petproject.frontendservice.course.controller.payload.NewCoursePayload;
import ru.itche.petproject.frontendservice.course_subjects.client.CourseSubjectsRestClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("musical-school/course-subjects")
public class CourseSubjectsController {

    private final CourseSubjectsRestClient restClient;

    @GetMapping("list")
    public String getCoursesList(Model model) {
        model.addAttribute("courseSubjects", this.restClient.getAllCourseSubjects());
        return "course_subject/list";
    }

//    @GetMapping("create")
//    public String createCoursePage() {
//        return "course/create";
//    }
//
//    @PostMapping("create")
//    public String createCourse(NewCoursePayload payload) {
//        this.courseRestClient.createCourse(payload.title(), payload.titleCurriculum());
//        return "redirect:/musical-school/courses/list";
//    }
}
