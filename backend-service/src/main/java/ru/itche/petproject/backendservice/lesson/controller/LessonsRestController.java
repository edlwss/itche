package ru.itche.petproject.backendservice.lesson.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.itche.petproject.backendservice.lesson.controller.payload.NewLessonPayload;
import ru.itche.petproject.backendservice.lesson.entity.Lesson;
import ru.itche.petproject.backendservice.lesson.service.LessonService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("musical-school-api/lessons")
public class LessonsRestController {

    private final LessonService lessonService;

    @GetMapping()
    public Map<LocalDate, List<Lesson>> getGroupSchedule(
            @RequestParam Integer groupId,
            @RequestParam int year,
            @RequestParam int month) {
        return lessonService.getCalendarFormattedSchedule(groupId, year, month);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> generatePdfReport(@RequestParam Integer groupId,
                                                    @RequestParam int year,
                                                    @RequestParam int month) {

        // Генерация PDF отчета
        byte[] pdfBytes = lessonService.generateSchedulePdf(groupId, year, month);

        // Настройка заголовков для ответа
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "lessons" + ".pdf");

        // Возвращение PDF в виде байтового массива
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    @GetMapping({"/{lessonId:\\d+}"})
    public Lesson getLesson(@PathVariable Integer lessonId) {
        return lessonService.findLessonId(lessonId);
    }

    @PostMapping
    public ResponseEntity<Lesson> createLesson (@RequestBody NewLessonPayload payload,
                                                 UriComponentsBuilder uriBuilder) {
        Lesson lesson = this.lessonService.createLesson(payload.group(), payload.subject(),payload.timeLesson(),
                payload.dateLesson());

        return ResponseEntity.created(uriBuilder
                        .replacePath("/musical-school-api/lessons/lesson/{lessonId}")
                        .build(Map.of("lessonId", lesson.getId())))
                .body(lesson);
    }

    @DeleteMapping("/{lessonId:\\d+}/delete")
    public void deleteLesson(@PathVariable Integer lessonId) {
        this.lessonService.deleteLesson(lessonId);
    }

}
