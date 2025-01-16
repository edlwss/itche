package ru.itche.petproject.frontendservice.student.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itche.petproject.frontendservice.group.client.ImplGroupRestClient;
import ru.itche.petproject.frontendservice.instrument.client.InstrumentRestClient;
import ru.itche.petproject.frontendservice.instrument.entity.Instrument;
import ru.itche.petproject.frontendservice.student.client.StudentRestClient;
import ru.itche.petproject.frontendservice.student.controller.payload.UpdateStudentPayload;
import ru.itche.petproject.frontendservice.student.entityRecord.Student;
import ru.itche.petproject.frontendservice.user.client.UserRestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@RequiredArgsConstructor
@RequestMapping("/musical-school/students/student/{studentId:\\d+}")
public class StudentController {

    private final StudentRestClient studentRestClient;
    private final HttpSession session;
    private final ImplGroupRestClient groupRestClient;
    private final InstrumentRestClient instrumentRestClient;

    @ModelAttribute("student")
    public Student getStudent(@PathVariable Integer studentId) {
        return this.studentRestClient.findStudent(studentId).orElseThrow();
    }
    @ModelAttribute("role")
    public String getRole() {
        return this.session.getAttribute("role").toString();
    }

    @ModelAttribute("userId")
    public String getUserId() {
        return this.session.getAttribute("userId").toString();
    }

    @GetMapping()
    public String getStudentDetails(Model model, @PathVariable Integer studentId) {
        model.addAttribute("instrument",
                instrumentRestClient.findInstrumentsByUser(this.getStudent(studentId).user().id()));
        return "student/details";
    }

    @GetMapping("/edit")
    public String editStudentForm(Model model){
        model.addAttribute("groups", this.groupRestClient.findListGroups());
        return "student/edit";
    }

    @PostMapping("edit")
    public String updateStudent(@ModelAttribute("student") Student student,
                                UpdateStudentPayload payload,
                                @RequestParam("currentPhoto") String currentPhoto,
                                @RequestParam("photo") MultipartFile photo) throws IOException {

        String photoName;

        if (!photo.isEmpty()) {
            // Пользователь загрузил новое фото
            photoName = photo.getOriginalFilename();
            Path filePath = Paths.get("frontend-service/src/main/resources/static/img", photoName);
            Files.write(filePath, photo.getBytes());
        } else {
            // Пользователь не выбрал новое фото, оставляем текущее
            photoName = currentPhoto;
        }

        studentRestClient.updateStudent(
                student.id(),
                payload.group(),
                payload.details(),
                payload.userPayload().firstName(),
                payload.userPayload().lastName(),
                payload.userPayload().middleName(),
                payload.userPayload().dateOfBirth(),
                photoName,
                payload.userPayload().phoneNumber(),
                payload.userPayload().email(),
                payload.userPayload().idCardPayload().passportSeries(),
                payload.userPayload().idCardPayload().passportNumber(),
                payload.userPayload().idCardPayload().issuedBy(),
                payload.userPayload().idCardPayload().issueDate(),
                payload.userPayload().idCardPayload().birthCertificateNumber(),
                payload.userPayload().addressPayload().city(),
                payload.userPayload().addressPayload().street(),
                payload.userPayload().addressPayload().home(),
                payload.userPayload().addressPayload().flat()
        );
        return "redirect:/musical-school/groups/group-students/" + student.group().id();
    }


    @PostMapping("delete")
    public String deleteStudent(@ModelAttribute("student") Student student) {
        this.studentRestClient.deleteStudent(student.id());
        return "redirect:/musical-school/groups/group-students/" + student.group().id();
    }


}

