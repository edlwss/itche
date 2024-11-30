package ru.itche.petproject.frontendservice.course_subjects.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itche.petproject.frontendservice.course.controller.payload.NewCoursePayload;
import ru.itche.petproject.frontendservice.course.entityRecord.Course;
import ru.itche.petproject.frontendservice.course_subjects.client.CourseSubjectsRestClient;
import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("musical-school/course-subjects/{courseId:\\d+}")
public class CourseSubjectsController {

    private final CourseSubjectsRestClient restClient;

    @GetMapping
    public String getCourseSubjects(@PathVariable Integer courseId, Model model) {
        Map<String, List<Subject>> courseSubjects = this.restClient.getSubjectsByCourse(courseId);
        model.addAttribute("courseSubjects", courseSubjects);
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
