package ru.itche.petproject.frontendservice.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itche.petproject.frontendservice.student.client.StudentRestClient;
import ru.itche.petproject.frontendservice.student.controller.payload.NewStudentPayload;
import ru.itche.petproject.frontendservice.user.controller.payload.NewUserPayload;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentRestClient studentRestClient;

    @GetMapping("list")
    public String getStudentsList(Model model) {
        model.addAttribute("students", this.studentRestClient.getAllStudents());
        return "student/list";
    }

    @GetMapping("create")
    public String createStudentPage(Model model) {
        model.addAttribute("studentPayload", new NewStudentPayload(null, null, null));
        return "student/create";
    }

    @PostMapping("create")
    public String createStudent(@ModelAttribute NewStudentPayload payload) {
        this.studentRestClient.createStudent(payload);
        return "redirect:/musical-school/students/list";
    }

    @GetMapping("{id}")
    public String getStudentDetails(@PathVariable("id") int studentId, Model model) {
        model.addAttribute("student", this.studentRestClient.findStudent(studentId).orElseThrow());
        return "student/details";
    }

    @PostMapping("{id}/delete")
    public String deleteStudent(@PathVariable("id") int studentId) {
        this.studentRestClient.deleteStudent(studentId);
        return "redirect:/musical-school/students/list";
    }

//    // Форма для редактирования информации о студенте
//    @GetMapping("/{id}/edit")
//    public String editStudentForm(@PathVariable("id") int studentId, Model model) {
//        model.addAttribute("student", studentRestClient.findStudent(studentId).orElseThrow());
//        model.addAttribute("updatePayload", new UpdateStudentPayload());
//        return "students/edit";
//    }
//
//    // Обработка редактирования информации о студенте
//    @PostMapping("/{id}/edit")
//    public String updateStudent(@PathVariable("id") int studentId,
//                                @ModelAttribute("updatePayload") UpdateStudentPayload payload) {
//        studentRestClient.updateStudent(studentId, payload);
//        return "redirect:/students/" + studentId;
//    }
}

