package ru.itche.petproject.frontendservice.subject.client;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import ru.itche.petproject.frontendservice.subject.controller.payload.NewSubjectPayload;
import ru.itche.petproject.frontendservice.subject.controller.payload.UpdateSubjectPayload;
import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ImplSubjectRestClient implements SubjectRestClient {

    private final RestClient restClient;
    private final HttpSession session;

    private static final ParameterizedTypeReference<List<Subject>> SUBJECT_TYPE_REFERENCE =
            new ParameterizedTypeReference<>(){};


    @Override
    public List<Subject> getAllSubjects() {

        String token = (String) session.getAttribute("token");

        return restClient
                .get()
                .uri("/musical-school-api/subjects")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(SUBJECT_TYPE_REFERENCE);
    }

    @Override
    public void createSubject(String title, String syllabus) {

        String token = (String) session.getAttribute("token");

        restClient
                .post()
                .uri("/musical-school-api/subjects")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new NewSubjectPayload(title, syllabus))
                .retrieve()
                .body(Subject.class);
    }

    @Override
    public Optional<Subject> findSubject(int subjectId) {

        String token = (String) session.getAttribute("token");

        return Optional.ofNullable(this.restClient.get()
                .uri("/musical-school-api/subjects/subject/{subjectId}", subjectId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(Subject.class));

    }

    @Override
    public void updateSubject(int subjectId, String title, String syllabus) {

        String token = (String) session.getAttribute("token");

        this.restClient
                .patch()
                .uri("/musical-school-api/subjects/subject/{subjectId}", subjectId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new UpdateSubjectPayload(title, syllabus))
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void deleteSubject(int subjectId) {

        String token = (String) session.getAttribute("token");

        this.restClient.delete()
                .uri("/musical-school-api/subjects/subject/{subjectId}", subjectId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .toBodilessEntity();
    }
}
