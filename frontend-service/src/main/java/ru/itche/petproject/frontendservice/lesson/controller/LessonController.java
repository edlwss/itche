package ru.itche.petproject.frontendservice.lesson.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itche.petproject.frontendservice.course.controller.payload.NewCoursePayload;
import ru.itche.petproject.frontendservice.group.client.GroupsRestClient;
import ru.itche.petproject.frontendservice.group.entityRecord.Group;
import ru.itche.petproject.frontendservice.lesson.client.LessonRestClient;
import ru.itche.petproject.frontendservice.lesson.controller.payload.NewLessonPayload;
import ru.itche.petproject.frontendservice.lesson.entityRecord.Lesson;
import ru.itche.petproject.frontendservice.subject.client.SubjectRestClient;
import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("musical-school/lessons")
public class LessonController {

    private final LessonRestClient lessonRestClient;
    private final GroupsRestClient groupsRestClient;
    private final SubjectRestClient subjectRestClient;
    private  final HttpSession session;

    @ModelAttribute("userId")
    public String getUserId(HttpSession session) {
        return session.getAttribute("userId").toString();
    }

    @ModelAttribute("role")
    public String getRole() {
        return this.session.getAttribute("role").toString();
    }

    @GetMapping()
    public String getGroupSchedulePage(
            @RequestParam Integer groupId,
            @RequestParam Integer year,
            @RequestParam Integer month,
            Model model) {

        Map<LocalDate, List<Lesson>> schedule =
                lessonRestClient.getGroupSchedule(groupId, year, month);

        List<Group> groups = groupsRestClient.findListGroups();

        model.addAttribute("lesson", schedule);
        model.addAttribute("groupId", groupId);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("groups", groups);

        return "lesson/lesson_list";
    }

    @GetMapping("create")
    public String createCoursePage(Model model,
                                   @RequestParam("groupId") Integer groupId,
                                   @RequestParam("data") LocalDate date) {

        model.addAttribute("groupId", groupId);
        model.addAttribute("date", date);
        model.addAttribute("groups", groupsRestClient.findListGroups());
        model.addAttribute("subjects", subjectRestClient.getAllSubjects());
        return "lesson/create";
    }

    @PostMapping("create")
    public String createLesson(NewLessonPayload payload) {
        System.out.println(payload);
        this.lessonRestClient.createLesson(payload.group(), payload.subject(), payload.timeLesson(),
                payload.dateLesson());
        return "redirect:/musical-school/lessons?groupId=1&year=2025&month=1";
    }

    @PostMapping("/{lessonId:\\d+}")
    public String deleteLesson(@PathVariable Integer lessonId,
                               @RequestParam Integer groupId,
                               @RequestParam Integer year,
                               @RequestParam Integer month
                               ) {
        this.lessonRestClient.deleteLesson(lessonId);
        return "redirect:/musical-school/lessons?groupId="+groupId+"&year="+year+"&month="+month;
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> downloadGradePdf(@RequestParam("groupId") Integer groupId,
                                                   @RequestParam("year") Integer year,
                                                   @RequestParam("month") Integer month) {

        byte[] pdfContent = lessonRestClient.getGradePdf(groupId, year, month);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=lessons" + ".pdf");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
}
