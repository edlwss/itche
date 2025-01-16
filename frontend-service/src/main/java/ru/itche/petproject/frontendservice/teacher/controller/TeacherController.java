package ru.itche.petproject.frontendservice.teacher.controller;

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
import org.springframework.web.multipart.MultipartFile;
import ru.itche.petproject.frontendservice.instrument.client.InstrumentRestClient;
import ru.itche.petproject.frontendservice.subject.client.SubjectRestClient;
import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;
import ru.itche.petproject.frontendservice.teacher.client.TeacherRestClient;
import ru.itche.petproject.frontendservice.teacher.controller.payload.UpdateTeacherPayload;
import ru.itche.petproject.frontendservice.teacher.entityRecord.Teacher;
import ru.itche.petproject.frontendservice.user.client.UserRestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/musical-school/teachers/teacher/{teacherId:\\d+}")
public class TeacherController {

    private final TeacherRestClient teacherRestClient;
    private final HttpSession session;
    private final SubjectRestClient subjectRestClient;
    private final InstrumentRestClient instrumentRestClient;


    @ModelAttribute("teacher")
    public Teacher getTeacher(@PathVariable Integer teacherId) {
        return this.teacherRestClient.findTeacher(teacherId).orElseThrow();
    }

    @ModelAttribute("teacher_subjects")
    public List<Subject> getSubjectsByTeacher(@PathVariable Integer teacherId) {
        return this.subjectRestClient.getSubjectsByTeacher(teacherId);
    }

    @ModelAttribute("role")
    public String getRole() {
        return this.session.getAttribute("role").toString();
    }

    @ModelAttribute("userId")
    public String getUserId() {
        return session.getAttribute("userId").toString();
    }

    @GetMapping()
    public String getTeacherDetails(Model model, @PathVariable Integer teacherId) {
        model.addAttribute("instrument",
                instrumentRestClient.findInstrumentsByUser(this.getTeacher(teacherId).user().id()));
        return "teacher/details";
    }

    @GetMapping("edit")
    public String editTeacherForm(Model model) {
        return "teacher/edit";
    }

    @PostMapping("edit")
    public String updateTeacher(@ModelAttribute("teacher") Teacher teacher,
                                @ModelAttribute UpdateTeacherPayload payload,
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

        teacherRestClient.updateTeacher(
                teacher.id(),
                payload.education(),
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
        return "redirect:/musical-school/teachers/teacher/" + teacher.id();
    }


    @PostMapping("/delete")
    public String deleteTeacher(@PathVariable Integer teacherId) {
        this.teacherRestClient.deleteTeacher(teacherId);
        return "redirect:/musical-school/teachers/list";
    }

    @GetMapping("/add")
    public String showAddSubjectsPage(Model model) {
        Iterable<Subject> subjects = subjectRestClient.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "teacher/add_subjects";
    }

    @PostMapping("add")
    public String addSubjectsToTeacher(@PathVariable Integer teacherId,
                                      @RequestParam List<Integer> subjectIds) {
        subjectRestClient.updateChageTeacher(teacherId, subjectIds);
        return "redirect:/musical-school/teachers/teacher/%d".formatted(teacherId);
    }
}
