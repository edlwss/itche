package ru.itche.petproject.frontendservice.course_subjects.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;
import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ImplCourseSubjectsClient implements CourseSubjectsRestClient {

    private final RestClient restClient;
    private final HttpSession session; // Добавляем HttpSession как зависимость

    private static final ParameterizedTypeReference<Map<String, List<Subject>>> COURSE_SUBJECTS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {};

    @Override
    public Map<String, List<Subject>> getSubjectsByCourse(Integer courseId) {
        // Извлекаем токен из сессии
        String token = (String) session.getAttribute("token");
        return restClient
                .get()
                .uri("/musical-school-api/course-subjects/{courseId}", courseId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(COURSE_SUBJECTS_TYPE_REFERENCE);
    }

    @Override
    public void addSubjectsToCourse(Integer courseId, List<Integer> subjectIds) {
        String token = (String) session.getAttribute("token");
        restClient
                .post()
                .uri("/musical-school-api/course-subjects/{courseId}/add", courseId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(subjectIds)
                .retrieve()
                .toBodilessEntity();
    }
}
