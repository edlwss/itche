package ru.itche.petproject.frontendservice.course.controller;

import jakarta.servlet.http.HttpSession;
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


@Controller
@RequiredArgsConstructor
@RequestMapping("musical-school/courses/course/{courseId:\\d+}")
public class CourseController {

    private final CourseRestClient courseRestClient;
    private  final HttpSession session;

    @ModelAttribute("role")
    public String getRole() {
        return this.session.getAttribute("role").toString();
    }

    @ModelAttribute("course")
    public Course course(@PathVariable("courseId") Integer courseId) {
        return this.courseRestClient.findCourse(courseId).orElseThrow();
    }


    @GetMapping("edit")
    public String getCourseEditPage() {
        return "course/edit";
    }

    @PostMapping("edit")
    public String updateCourse(@ModelAttribute("course") Course course, UpdateCoursePayload payload) {
        this.courseRestClient.updateCourse(course.id(), payload.title(), payload.titleCurriculum());
        return "redirect:/musical-school/courses/list";
    }

    @PostMapping("delete")
    public String deleteCourse(@ModelAttribute("course") Course course) {
        this.courseRestClient.deleteCourse(course.id());
        return "redirect:/musical-school/courses/list";
    }
}
