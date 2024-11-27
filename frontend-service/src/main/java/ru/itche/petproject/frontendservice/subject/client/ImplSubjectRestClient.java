package ru.itche.petproject.frontendservice.subject.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
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

    private static final ParameterizedTypeReference<List<Subject>> SUBJECT_TYPE_REFERENCE =
            new ParameterizedTypeReference<>(){};

    @Override
    public List<Subject> getAllSubjects() {
        return restClient
                .get()
                .uri("/musical-school-api/subjects")
                .retrieve()
                .body(SUBJECT_TYPE_REFERENCE);
    }

    @Override
    public void createSubject(String title, String syllabus) {
        restClient
                .post()
                .uri("/musical-school-api/subjects")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new NewSubjectPayload(title, syllabus))
                .retrieve()
                .body(Subject.class);
    }

    @Override
    public Optional<Subject> findSubject(int subjectId) {
        return Optional.ofNullable(this.restClient.get()
                .uri("/musical-school-api/subjects/subject/{subjectId}", subjectId)
                .retrieve()
                .body(Subject.class));

    }

    @Override
    public void updateSubject(int subjectId, String title, String syllabus) {
        this.restClient
                .patch()
                .uri("/musical-school-api/subjects/subject/{subjectId}", subjectId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new UpdateSubjectPayload(title, syllabus))
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void deleteSubject(int subjectId) {
        this.restClient.delete()
                .uri("/musical-school-api/subjects/subject/{subjectId}", subjectId)
                .retrieve()
                .toBodilessEntity();
    }
}
