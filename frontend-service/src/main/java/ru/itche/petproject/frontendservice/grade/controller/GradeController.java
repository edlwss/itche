package ru.itche.petproject.frontendservice.grade.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itche.petproject.frontendservice.grade.client.GradeRestClient;
import ru.itche.petproject.frontendservice.grade.entityRecord.Grade;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/musical-school/grade")
public class GradeController {

    private final GradeRestClient gradeRestClient;
    private final HttpSession session;

    @ModelAttribute("role")
    public String getRole() {
        return this.session.getAttribute("role").toString();
    }

    @GetMapping
    public String gradeByGroup(Model model,
                               @RequestParam Integer groupId,
                               @RequestParam Integer lessonId) {
        model.addAttribute("groupId", groupId);
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("grades", gradeRestClient.getGradesByGroup(groupId, lessonId));

        return "grade/grade";
    }

    @PostMapping("/add")
    public String submitGrades(@RequestParam Integer groupId, @RequestParam Integer lessonId,
                               @RequestBody Map<String, Map<String, String>> data) {

        this.gradeRestClient.createGrades(groupId, lessonId, data);

        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        return "redirect:/musical-school/lessons?groupId=" + groupId + "&year=" + currentYear + "&month=" + currentMonth;
    }

    @GetMapping("/{studentId:\\d+}")
    public String gradeByStudentId(@PathVariable Integer studentId, Model model) {
        model.addAttribute("student_grade", gradeRestClient.getGradeByStudent(studentId));
        return "grade/student";
    }

    @GetMapping("/{studentId:\\d+}/pdf")
    public ResponseEntity<byte[]> downloadGradePdf(@PathVariable Integer studentId) {

        byte[] pdfContent = gradeRestClient.getGradePdfByStudent(studentId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=tabel_grade_" + studentId + ".pdf");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }

}