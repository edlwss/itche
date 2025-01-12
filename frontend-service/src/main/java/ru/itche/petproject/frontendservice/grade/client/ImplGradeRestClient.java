package ru.itche.petproject.frontendservice.grade.client;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.itche.petproject.frontendservice.grade.entityRecord.Grade;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ImplGradeRestClient implements GradeRestClient {

    private final RestClient restClient;
    private final HttpSession session;

    private static final ParameterizedTypeReference<List<Grade>> GRADE_LIST_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {};

    @Override
    public List<Grade> getGradesByGroup(Integer groupId, Integer lessonId) {
        String token = (String) session.getAttribute("token");

        return restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/musical-school-api/grade")
                        .queryParam("groupId", groupId)
                        .queryParam("lessonId", lessonId)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(GRADE_LIST_TYPE_REFERENCE);
    }

    @Override
    public List<Grade> createGrades(Integer groupId, Integer lessonId,
                                    Map<String, Map<String, String>> data) {
        String token = (String) session.getAttribute("token");

        return restClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/musical-school-api/grade")
                        .queryParam("groupId", groupId)
                        .queryParam("lessonId", lessonId)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .body(GRADE_LIST_TYPE_REFERENCE);
    }
}
