package ru.itche.petproject.frontendservice.student.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.itche.petproject.frontendservice.group.ImplGroupRestClient;
import ru.itche.petproject.frontendservice.student.client.StudentRestClient;
import ru.itche.petproject.frontendservice.student.controller.payload.NewStudentPayload;
import ru.itche.petproject.frontendservice.user.client.UserRestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/musical-school/students")
public class StudentsController {

    private final StudentRestClient studentRestClient;
    private final UserRestClient userRestClient;
    private final ImplGroupRestClient groupRestClient;

    @GetMapping("list")
    public String getStudentsList(Model model) {
        model.addAttribute("students", this.studentRestClient.getAllStudents());
        model.addAttribute("role", this.userRestClient.getUserRoleFromServer());
        return "student/list";
    }

    @GetMapping("create")
    public String createStudentPage(Model model){
        model.addAttribute("groups", groupRestClient.findAllGroups());
        return "student/create";
    }

    @PostMapping("create")
    public String createStudent(@ModelAttribute NewStudentPayload payload,
                                @RequestParam("photo") MultipartFile photo) throws IOException {

        String photoName = photo.getOriginalFilename();
        Path filePath = Paths.get("frontend-service/src/main/resources/static/img", photoName);
        Files.write(filePath, photo.getBytes());

        this.studentRestClient.createStudent(payload.group(),
                payload.details(),
                payload.userPayload().firstName(),
                payload.userPayload().lastName(),
                payload.userPayload().middleName(),
                payload.userPayload().dateOfBirth(),
                photoName,
                payload.userPayload().phoneNumber(),
                payload.userPayload().email(),
                payload.userPayload().username(),
                payload.userPayload().password());

        return "redirect:/musical-school/students/list";
    }
}
