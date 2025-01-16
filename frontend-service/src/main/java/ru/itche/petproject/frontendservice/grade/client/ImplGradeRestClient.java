package ru.itche.petproject.frontendservice.grade.client;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.itche.petproject.frontendservice.grade.entityRecord.Grade;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImplGradeRestClient implements GradeRestClient {

    private final RestClient restClient;
    private final HttpSession session;

    private static final ParameterizedTypeReference<List<Grade>> GRADE_LIST_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<Map<String, List<Number>>> GRADE_STUDENT_TYPE_REFERENCE =
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
    public void createGrades(Integer groupId, Integer lessonId,
                             Map<String, Map<String, String>> data) {
        String token = (String) session.getAttribute("token");

        restClient
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

    @Override
    public Map<String, List<Number>> getGradeByStudent(Integer studentId){

        String token = (String) session.getAttribute("token");
        return restClient
                .get()
                .uri("/musical-school-api/grade/{studentId}", studentId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(GRADE_STUDENT_TYPE_REFERENCE);
    }

    @Override
    public byte[] getGradePdfByStudent(Integer studentId) {
        String token = (String) session.getAttribute("token");

        // Выполняем GET-запрос для получения PDF
        return restClient
                .get()
                .uri("/musical-school-api/grade/{studentId}/pdf", studentId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(byte[].class); // Возвращаем PDF в виде массива байтов
    }

}
