package ru.itche.petproject.frontendservice.group.client;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;
import ru.itche.petproject.frontendservice.course.entityRecord.Course;
import ru.itche.petproject.frontendservice.group.entityRecord.Group;
import ru.itche.petproject.frontendservice.student.entityRecord.Student;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ImplGroupRestClient implements GroupsRestClient{

    private final RestClient restClient;
    private final HttpSession session;

    private static final ParameterizedTypeReference <Map<String, List<Group>>> GROUP_TYPE_REFERENCE =
            new ParameterizedTypeReference<>(){};
    private static final ParameterizedTypeReference <List<Group>> GROUP_LIST_TYPE_REFERENCE =
            new ParameterizedTypeReference<>(){};
    private static final ParameterizedTypeReference <Map<String, List<Student>>> STUDENT_TYPE_REFERENCE =
            new ParameterizedTypeReference<>(){};

    @Override
    public Map<String, List<Group>> findAllGroups() {

        String token = (String) session.getAttribute("token");

        return restClient
                .get()
                .uri("/musical-school-api/groups")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(GROUP_TYPE_REFERENCE);
    }

    @Override
    public List<Group> findListGroups() {

        String token = (String) session.getAttribute("token");

        return restClient
                .get()
                .uri("/musical-school-api/groups/list")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(GROUP_LIST_TYPE_REFERENCE);
    }

    @Override
    public Map<String, List<Student>> findStudentsByGroups(Integer groupId) {

        String token = (String) session.getAttribute("token");

        return restClient
                .get()
                .uri("/musical-school-api/groups/group-students/{groupId}", groupId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(STUDENT_TYPE_REFERENCE);
    }


}
