package ru.itche.petproject.frontendservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.itche.petproject.frontendservice.course.client.ImplCourseRestClient;
import ru.itche.petproject.frontendservice.subject.client.ImplSubjectRestClient;
import ru.itche.petproject.frontendservice.course_subjects.client.ImplCourseSubjectsClient;

@Configuration
public class ClientBeans {

    @Bean
    public ImplCourseRestClient courseRestClient(
            @Value("${music-school.services.backend.uri:http://localhost:8081}") String baseUrl) {
        return new ImplCourseRestClient(RestClient.builder()
                .baseUrl(baseUrl)
                .build());
    }
    @Bean
    public ImplSubjectRestClient subjectRestClient(
            @Value("${music-school.services.backend.uri:http://localhost:8081}") String baseUrl) {
        return new ImplSubjectRestClient(RestClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    @Bean
    public ImplCourseSubjectsClient courseSubjectsRestClient(
            @Value("${music-school.services.backend.uri:http://localhost:8081}") String baseUrl) {
        return new ImplCourseSubjectsClient(RestClient.builder()
                .baseUrl(baseUrl)
                .build());
    }
}
