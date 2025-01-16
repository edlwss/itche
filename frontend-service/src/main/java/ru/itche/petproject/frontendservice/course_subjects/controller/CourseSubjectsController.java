package ru.itche.petproject.frontendservice.course_subjects.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itche.petproject.frontendservice.course_subjects.client.CourseSubjectsRestClient;
import ru.itche.petproject.frontendservice.subject.client.SubjectRestClient;
import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("musical-school/course-subjects/{courseId:\\d+}")
public class CourseSubjectsController {

    private final CourseSubjectsRestClient restClient;
    private final SubjectRestClient subjectRestClient;
    private  final HttpSession session;

    @ModelAttribute("role")
    public String getRole() {
        return this.session.getAttribute("role").toString();
    }

    @GetMapping
    public String getCourseSubjects(@PathVariable Integer courseId, Model model) {
        Map<String, List<Subject>> courseSubjects = this.restClient.getSubjectsByCourse(courseId);
        model.addAttribute("courseSubjects", courseSubjects);
        return "course_subject/list";
    }

    @GetMapping("add")
    public String showAddSubjectsPage(@PathVariable Integer courseId, Model model) {
        List<Subject> allSubjects = subjectRestClient.getAllSubjects();
        Map<String, List<Subject>> courseSubjects = restClient.getSubjectsByCourse(courseId);

        List<Subject> subjectsInCourse = courseSubjects.values().stream()
                .flatMap(List::stream)
                .toList();

        List<Subject> availableSubjects = allSubjects.stream()
                .filter(subject -> subjectsInCourse.stream()
                        .noneMatch(courseSubject -> courseSubject.id().equals(subject.id())))
                .toList();

        model.addAttribute("subjects", availableSubjects);
        model.addAttribute("courseId", courseId);
        return "course_subject/add_subjects";
    }


    @PostMapping("add")
    public String addSubjectsToCourse(@PathVariable Integer courseId,
                                      @RequestParam List<Integer> subjectIds) {
        restClient.addSubjectsToCourse(courseId, subjectIds);
        return "redirect:/musical-school/course-subjects/%d".formatted(courseId);
    }

    @PostMapping("/{subjectId:\\d+}")
    public String deleteSubjectToCourse(@PathVariable("courseId") Integer courseId,
                                        @PathVariable("subjectId") Integer subjectId) {
        restClient.deleteSubjectsFromCourse(courseId, subjectId);
        return "redirect:/musical-school/course-subjects/%d".formatted(courseId);
    }
}
