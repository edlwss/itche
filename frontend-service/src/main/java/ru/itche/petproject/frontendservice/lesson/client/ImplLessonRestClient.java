package ru.itche.petproject.frontendservice.lesson.client;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.itche.petproject.frontendservice.course.controller.payload.NewCoursePayload;
import ru.itche.petproject.frontendservice.course.entityRecord.Course;
import ru.itche.petproject.frontendservice.lesson.controller.payload.NewLessonPayload;
import ru.itche.petproject.frontendservice.lesson.entityRecord.Lesson;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ImplLessonRestClient implements LessonRestClient {

    private final RestClient restClient;
    private final HttpSession session;

    private static final ParameterizedTypeReference<Map<LocalDate, List<Lesson>>> LESSON_TYPE_REFERENCE =
            new ParameterizedTypeReference<>(){};
    @Override
    public Map<LocalDate, List<Lesson>> getGroupSchedule(Integer groupId, int year, int month) {

        String token = (String) session.getAttribute("token");

        return restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/musical-school-api/lessons")
                        .queryParam("year", year)
                        .queryParam("month", month)
                        .queryParam("groupId", groupId)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(LESSON_TYPE_REFERENCE);
    }

    @Override
    public void createLesson(Integer group, Integer subject, LocalTime timeLesson,
                             LocalDate dateLesson) {

        String token = (String) session.getAttribute("token");

        restClient
                .post()
                .uri("/musical-school-api/lessons")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new NewLessonPayload(group, subject, timeLesson, dateLesson))
                .retrieve()
                .body(Lesson.class);
    }

}
