package ru.itche.petproject.frontendservice.student.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itche.petproject.frontendservice.group.client.ImplGroupRestClient;
import ru.itche.petproject.frontendservice.student.client.StudentRestClient;
import ru.itche.petproject.frontendservice.student.controller.payload.UpdateStudentPayload;
import ru.itche.petproject.frontendservice.student.entityRecord.Student;
import ru.itche.petproject.frontendservice.user.client.UserRestClient;


@Controller
@RequiredArgsConstructor
@RequestMapping("/musical-school/students/student/{studentId:\\d+}")
public class StudentController {

    private final StudentRestClient studentRestClient;
    private final HttpSession session;
    private final ImplGroupRestClient groupRestClient;

    @ModelAttribute("student")
    public Student getStudent(@PathVariable Integer studentId) {
        return this.studentRestClient.findStudent(studentId).orElseThrow();
    }
    @ModelAttribute("role")
    public String getRole() {
        return this.session.getAttribute("role").toString();
    }

    @GetMapping()
    public String getStudentDetails() {
        return "student/details";
    }

    @GetMapping("/edit")
    public String editStudentForm(Model model){
        model.addAttribute("groups", this.groupRestClient.findListGroups());
        return "student/edit";
    }

    @PostMapping("edit")
    public String updateStudent(@ModelAttribute("student") Student student,
                                UpdateStudentPayload payload) {

        studentRestClient.updateStudent(
                student.id(),
                payload.group(),
                payload.details(),
                payload.updateUserPayload().firstName(),
                payload.updateUserPayload().lastName(),
                payload.updateUserPayload().middleName(),
                payload.updateUserPayload().dateOfBirth(),
                payload.updateUserPayload().photo(),
                payload.updateUserPayload().phoneNumber(),
                payload.updateUserPayload().email()
        );
        return "redirect:/musical-school/students/student/" + student.id();
    }


    @PostMapping("delete")
    public String deleteStudent(@ModelAttribute("student") Student student) {
        this.studentRestClient.deleteStudent(student.id());
        return "redirect:/musical-school/students/list";
    }


}

