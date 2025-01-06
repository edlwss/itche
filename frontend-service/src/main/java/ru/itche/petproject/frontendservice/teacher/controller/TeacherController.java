package ru.itche.petproject.frontendservice.teacher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itche.petproject.frontendservice.student.controller.payload.UpdateStudentPayload;
import ru.itche.petproject.frontendservice.student.entityRecord.Student;
import ru.itche.petproject.frontendservice.subject.client.SubjectRestClient;
import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;
import ru.itche.petproject.frontendservice.teacher.client.TeacherRestClient;
import ru.itche.petproject.frontendservice.teacher.client.TeacherSubjectsRestClient;
import ru.itche.petproject.frontendservice.teacher.controller.payload.UpdateTeacherPayload;
import ru.itche.petproject.frontendservice.teacher.entityRecord.Teacher;
import ru.itche.petproject.frontendservice.teacher.entityRecord.TeacherSubjects;
import ru.itche.petproject.frontendservice.user.client.UserRestClient;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/musical-school/teachers/teacher/{teacherId:\\d+}")
public class TeacherController {

    private final TeacherRestClient teacherRestClient;
    private final UserRestClient userRestClient;
    private final TeacherSubjectsRestClient teacherSubjectsRestClient;
    private final SubjectRestClient subjectRestClient;

    @ModelAttribute("teacher")
    public Teacher getTeacher(@PathVariable Integer teacherId) {
        return this.teacherRestClient.findTeacher(teacherId).orElseThrow();
    }

    @ModelAttribute("teacher_subjects")
    public Map<String, List<Subject>> getSubjectsByTeacher(@PathVariable Integer teacherId) {
        return this.teacherSubjectsRestClient.getSubjectsByTeacher(teacherId);
    }

    @ModelAttribute("role")
    public String getRole() {
        return this.userRestClient.getUserRoleFromServer();
    }

    @GetMapping()
    public String getTeacherDetails() {
        return "teacher/details";
    }

    @GetMapping("/edit")
    public String editTeacherForm(Model model) {
        return "teacher/edit";
    }

    @PostMapping("edit")
    public String updateTeacher(@ModelAttribute("teacher") Teacher teacher,
                                UpdateTeacherPayload payload) {

        teacherRestClient.updateTeacher(
                teacher.id(),
                payload.education(),
                payload.details(),
                payload.userPayload().firstName(),
                payload.userPayload().lastName(),
                payload.userPayload().middleName(),
                payload.userPayload().dateOfBirth(),
                payload.userPayload().photo(),
                payload.userPayload().phoneNumber(),
                payload.userPayload().email()
        );
        return "redirect:/musical-school/teachers/teacher/" + teacher.id();
    }


    @PostMapping("delete")
    public String deleteTeacher(@ModelAttribute("teacher") Teacher teacher) {
        this.teacherRestClient.deleteTeacher(teacher.id());
        return "redirect:/musical-school/teachers/list";
    }

    @GetMapping("add")
    public String showAddSubjectsPage(Model model) {
        Iterable<Subject> subjects = subjectRestClient.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "teacher/add_subjects";
    }

    @PostMapping("add")
    public String addSubjectsToTeacher(@PathVariable Integer teacherId,
                                      @RequestParam List<Integer> subjectIds) {
        teacherSubjectsRestClient.addSubjectsToTeacher(teacherId, subjectIds);
        return "redirect:/musical-school/teachers/teacher/%d".formatted(teacherId);
    }
}
