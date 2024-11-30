package ru.itche.petproject.frontendservice.course_subjects.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import ru.itche.petproject.frontendservice.course.controller.payload.NewCoursePayload;
import ru.itche.petproject.frontendservice.course.controller.payload.UpdateCoursePayload;
import ru.itche.petproject.frontendservice.course.entityRecord.Course;
import ru.itche.petproject.frontendservice.course_subjects.controller.payload.NewCourseSubjectsPayload;
import ru.itche.petproject.frontendservice.course_subjects.controller.payload.UpdateCourseSubjectsPayload;
import ru.itche.petproject.frontendservice.course_subjects.entityRecord.CourseSubjects;
import ru.itche.petproject.frontendservice.subject.entityRecord.Subject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class ImplCourseSubjectsClient implements CourseSubjectsRestClient{

    private final RestClient restClient;

    private static final ParameterizedTypeReference<List<CourseSubjects>> COUR_SUB_TYPE_REFERENCE =
            new ParameterizedTypeReference<>(){};

    private static final ParameterizedTypeReference<Map<String, List<Subject>>> COURSE_SUBJECTS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {};

    @Override
    public Map<String, List<Subject>> getSubjectsByCourse(Integer courseId) {
        return restClient
                .get()
                .uri("/musical-school-api/course-subjects/{courseId}", courseId)
                .retrieve()
                .body(COURSE_SUBJECTS_TYPE_REFERENCE);
    }

    @Override
    public List<CourseSubjects> getAllCourseSubjects() {
        return restClient
                .get()
                .uri("/musical-school-api/course-subjects")
                .retrieve()
                .body(COUR_SUB_TYPE_REFERENCE);
    }

    @Override
    public void setAllSubjectsToCourse(Integer courseId, List<Integer> subjectIds) {
        restClient
                .post()
                .uri("/musical-school-api/course-subjects")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new NewCourseSubjectsPayload(courseId, subjectIds))
                .retrieve()
                .body(CourseSubjects.class);
    }

    @Override
    public Optional<CourseSubjects> findCourseSubjects(int courSubId) {
        return Optional.ofNullable( this.restClient.get()
                .uri("/musical-school-api/course-subjects/{courSubId}", courSubId)
                .retrieve()
                .body(CourseSubjects.class));

    }

    @Override
    public void updateCourseSubjects(int courSubId, String details) {
        this.restClient
                .patch()
                .uri("/musical-school-api/course-subjects/{courSubId}", courSubId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new UpdateCourseSubjectsPayload(details))
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void deleteCourseSubjects(int courSubId) {
        this.restClient.delete()
                .uri("/musical-school-api/course-subjects/{courSubId}", courSubId)
                .retrieve()
                .toBodilessEntity();
    }
}
