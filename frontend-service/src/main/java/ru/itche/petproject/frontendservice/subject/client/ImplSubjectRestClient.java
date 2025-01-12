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
    public List<Subject> getSubjectsByTeacher(Integer teacherId) {

        String token = (String) session.getAttribute("token");

        return restClient
                .get()
                .uri("/musical-school-api/subjects/{teacherId}", teacherId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(SUBJECT_TYPE_REFERENCE);

    }

    @Override
    public void updateChageTeacher(Integer teacherId, List<Integer> subjectIds) {

        String token = (String) session.getAttribute("token");

        this.restClient
                .patch()
                .uri("/musical-school-api/subjects/{teacherId}", teacherId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(subjectIds)
                .retrieve()
                .toBodilessEntity();

    }

    @Override
    public void createSubject(String title, String syllabus, Integer teacherId) {

        String token = (String) session.getAttribute("token");

        restClient
                .post()
                .uri("/musical-school-api/subjects")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new NewSubjectPayload(title, syllabus, teacherId))
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
    public void updateSubject(int subjectId, String title, String syllabus, Integer teacherId) {

        String token = (String) session.getAttribute("token");

        this.restClient
                .patch()
                .uri("/musical-school-api/subjects/subject/{subjectId}", subjectId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new UpdateSubjectPayload(title, syllabus, teacherId))
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
