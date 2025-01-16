package ru.itche.petproject.frontendservice.teacher.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.itche.petproject.frontendservice.student.client.StudentRestClient;
import ru.itche.petproject.frontendservice.student.controller.payload.NewStudentPayload;
import ru.itche.petproject.frontendservice.subject.client.SubjectRestClient;
import ru.itche.petproject.frontendservice.teacher.client.TeacherRestClient;
import ru.itche.petproject.frontendservice.teacher.controller.payload.NewTeacherPayload;
import ru.itche.petproject.frontendservice.teacher.entityRecord.Teacher;
import ru.itche.petproject.frontendservice.user.client.UserRestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor
@RequestMapping("/musical-school/teachers")
public class TeachersController {

    private final TeacherRestClient teacherRestClient;
    private final SubjectRestClient subjectRestClient;
    private final HttpSession session;

    @GetMapping("/list")
    public String getTeachersList(Model model) {
        model.addAttribute("subjects_teachers", this.subjectRestClient.getTeachersWithSubjects());
        model.addAttribute("role", this.session.getAttribute("role"));

        return "teacher/list";
    }

    @GetMapping("create")
    public String createTeacherPage(Model model) {
        return "teacher/create";
    }

    @PostMapping("create")
    public String createTeacher(@ModelAttribute NewTeacherPayload payload,
                                @RequestParam("photo") MultipartFile photo) throws IOException {

        String photoName = photo.getOriginalFilename();
        Path filePath = Paths.get("frontend-service/src/main/resources/static/img", photoName);
        Files.write(filePath, photo.getBytes());

        Teacher teacher = this.teacherRestClient.createTeacher(payload.education(),
                payload.details(),
                payload.userPayload().firstName(),
                payload.userPayload().lastName(),
                payload.userPayload().middleName(),
                payload.userPayload().dateOfBirth(),
                photoName,
                payload.userPayload().phoneNumber(),
                payload.userPayload().email(),
                payload.userPayload().username(),
                payload.userPayload().password(),
                payload.userPayload().idCardPayload().passportSeries(),
                payload.userPayload().idCardPayload().passportNumber(),
                payload.userPayload().idCardPayload().issuedBy(),
                payload.userPayload().idCardPayload().issueDate(),
                payload.userPayload().idCardPayload().birthCertificateNumber(),
                payload.userPayload().addressPayload().city(),
                payload.userPayload().addressPayload().street(),
                payload.userPayload().addressPayload().home(),
                payload.userPayload().addressPayload().flat());

        return "redirect:/musical-school/teachers/teacher/" + teacher.id();
    }
}