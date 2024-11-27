package ru.itche.petproject.frontendservice.course.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import ru.itche.petproject.frontendservice.course.controller.payload.NewCoursePayload;
import ru.itche.petproject.frontendservice.course.controller.payload.UpdateCoursePayload;
import ru.itche.petproject.frontendservice.course.entityRecord.Course;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ImplCourseRestClient implements CourseRestClient {

    private final RestClient restClient;

    private static final ParameterizedTypeReference<List<Course>> COURSE_TYPE_REFERENCE =
        new ParameterizedTypeReference<>(){};

    @Override
    public List<Course> findAllCourses() {
        return restClient
                .get()
                .uri("/musical-school-api/courses")
                .retrieve()
                .body(COURSE_TYPE_REFERENCE);
    }

    @Override
    public void createCourse(String title, String curriculum) {
        restClient
                .post()
                .uri("/musical-school-api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new NewCoursePayload(title, curriculum))
                .retrieve()
                .body(Course.class);
    }

    @Override
    public Optional<Course> findCourse(int courseId) {
        return Optional.ofNullable( this.restClient.get()
                .uri("/musical-school-api/courses/course/{courseId}", courseId)
                .retrieve()
                .body(Course.class));

    }

    @Override
    public void updateCourse(int courseId, String title, String curriculum) {
         this.restClient
                .patch()
                .uri("/musical-school-api/courses/course/{courseId}", courseId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new UpdateCoursePayload(title, curriculum))
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void deleteCourse(int courseId) {
        this.restClient.delete()
                .uri("/musical-school-api/courses/course/{courseId}", courseId)
                .retrieve()
                .toBodilessEntity();
    }
}
