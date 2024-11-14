package ru.itche.petproject.frontendservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itche.petproject.frontendservice.controller.payload.UpdateCoursePayload;
import ru.itche.petproject.frontendservice.entity.Course;
import ru.itche.petproject.frontendservice.service.CourseService;

@Controller
@RequiredArgsConstructor
@RequestMapping("musical-school/courses/course/{courseId:\\d+}")
public class CourseController {

    private final CourseService courseService;

    //выносим повторящийся код для страницы отображения курса и страницы редактирования курса
    @ModelAttribute("course")
    public Course course(@PathVariable("courseId") Integer courseId) {
        return this.courseService.findCourse(courseId).orElseThrow();
    }

    @GetMapping()
    public String getCourse() {
        return "course/detail";
    }

    @GetMapping("edit")
    public String getCourseEditPage(@PathVariable("courseId") Integer courseId, Model model) {
        return "course/edit";
    }

    @PostMapping("edit")
    public String updateCourse(@ModelAttribute("course") Course course, UpdateCoursePayload payload) {
        this.courseService.updateCourse(course.getId(), payload.title(), payload.titleCurriculum());
        return "redirect:/musical-school/courses/course/%d".formatted(course.getId());
    }

    @PostMapping("delete")
    public String deleteCourse(@ModelAttribute("course") Course course) {
        this.courseService.deteleCourse(course.getId());
        return "redirect:/musical-school/courses/list";
    }
}
