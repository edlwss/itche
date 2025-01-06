package ru.itche.petproject.frontendservice.group;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;
import ru.itche.petproject.frontendservice.course.entityRecord.Course;

import java.util.List;

@RequiredArgsConstructor
public class ImplGroupRestClient {

    private final RestClient restClient;
    private final HttpSession session;

    private static final ParameterizedTypeReference<List<Course>> GROUP_TYPE_REFERENCE =
            new ParameterizedTypeReference<>(){};

    public List<Course> findAllGroups() {

        String token = (String) session.getAttribute("token");

        return restClient
                .get()
                .uri("/musical-school-api/groups")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(GROUP_TYPE_REFERENCE);
    }
}
