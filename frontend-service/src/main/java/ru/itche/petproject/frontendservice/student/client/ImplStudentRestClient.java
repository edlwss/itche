package ru.itche.petproject.frontendservice.student.client;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.itche.petproject.frontendservice.student.controller.payload.NewStudentPayload;
import ru.itche.petproject.frontendservice.student.entityRecord.Student;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ImplStudentRestClient implements StudentRestClient {

    private final RestClient restClient;

    private final HttpSession session;

    private static final ParameterizedTypeReference<List<Student>> STUDENT_TYPE_REFERENCE =
            new ParameterizedTypeReference<>(){};

    @Override
    public List<Student> getAllStudents() {
        String token = (String) session.getAttribute("token");

        return restClient
                .get()
                .uri("/musical-school-api/students")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(STUDENT_TYPE_REFERENCE);
    }

    @Override
    public void createStudent(NewStudentPayload payload) {
        String token = (String) session.getAttribute("token");

        restClient
                .post()
                .uri("/musical-school-api/students")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .body(Student.class);
    }

    @Override
    public Optional<Student> findStudent(int studentId) {
        String token = (String) session.getAttribute("token");

        return Optional.ofNullable(restClient
                .get()
                .uri("/musical-school-api/students/student/{studentId}", studentId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(Student.class));
    }

//    @Override
//    public void updateStudent(int studentId, UpdateStudentPayload payload) {
//        String token = (String) session.getAttribute("token");
//
//        restClient
//                .patch()
//                .uri("/musical-school-api/students/student/{studentId}", studentId)
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(payload)
//                .retrieve()
//                .toBodilessEntity();
//    }

    @Override
    public void deleteStudent(int studentId) {
        String token = (String) session.getAttribute("token");

        restClient
                .delete()
                .uri("/musical-school-api/students/student/{studentId}", studentId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .toBodilessEntity();
    }

}
