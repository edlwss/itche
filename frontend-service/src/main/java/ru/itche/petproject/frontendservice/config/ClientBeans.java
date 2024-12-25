package ru.itche.petproject.frontendservice.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.itche.petproject.frontendservice.course.client.ImplCourseRestClient;
import ru.itche.petproject.frontendservice.student.client.ImplStudentRestClient;
import ru.itche.petproject.frontendservice.subject.client.ImplSubjectRestClient;
import ru.itche.petproject.frontendservice.course_subjects.client.ImplCourseSubjectsClient;
import ru.itche.petproject.frontendservice.user.client.ImplUserRestController;


@Configuration
public class ClientBeans {

    @Bean
    public RestClient restClient(
            @Value("${music-school.services.backend.uri:http://localhost:8081}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public ImplCourseRestClient courseRestClient(RestClient restClient,
                                                 HttpSession session) {
        return new ImplCourseRestClient(restClient, session);
    }

    @Bean
    public ImplStudentRestClient studentRestClient(RestClient restClient,
                                                  HttpSession session) {
        return new ImplStudentRestClient(restClient, session);
    }

    @Bean
    public ImplSubjectRestClient subjectRestClient(RestClient restClient,
                                                   HttpSession session) {
        return new ImplSubjectRestClient(restClient, session);
    }

    @Bean
    public ImplCourseSubjectsClient courseSubjectsRestClient(RestClient restClient,
                                                             HttpSession session) {
        return new ImplCourseSubjectsClient(restClient, session);
    }

    @Bean
    public ImplUserRestController userRestClient(RestClient restClient) {
        return new ImplUserRestController(restClient);
    }
}
