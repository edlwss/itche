package ru.itche.petproject.frontendservice.teacher.client;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ImplTeacherSubjectsRestClient implements TeacherSubjectsRestClient {

    private final RestClient restClient;
    private final HttpSession session;

    private static final ParameterizedTypeReference<Map<String, List<Subject>>> TEACHER_SUBJECTS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {};

    @Override
    public Map<String, List<Subject>> getSubjectsByTeacher(Integer teacherId) {

        String token = (String) session.getAttribute("token");
        return restClient
                .get()
                .uri("/musical-school-api/teacher-subjects/{teacherId}", teacherId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(TEACHER_SUBJECTS_TYPE_REFERENCE);
    }

    @Override
    public void addSubjectsToTeacher(Integer teacherId, List<Integer> subjectIds) {
        String token = (String) session.getAttribute("token");
        restClient
                .post()
                .uri("/musical-school-api/teacher-subjects/{teacherId}/add", teacherId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(subjectIds)
                .retrieve()
                .toBodilessEntity();
    }

}
