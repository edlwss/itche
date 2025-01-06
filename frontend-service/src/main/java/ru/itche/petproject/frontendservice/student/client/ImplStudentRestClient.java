package ru.itche.petproject.frontendservice.student.client;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.itche.petproject.frontendservice.student.entityRecord.Student;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public void createStudent(Integer group,
                              String details,
                              String firstName,
                              String lastName,
                              String middleName,
                              LocalDate dateOfBirth,
                              String photo,
                              String phoneNumber,
                              String email,
                              String username,
                              String password) {

        String token = (String) session.getAttribute("token");

        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("lastName", lastName);
        userPayload.put("firstName", firstName);
        userPayload.put("middleName", middleName);
        userPayload.put("dateOfBirth", dateOfBirth);
        userPayload.put("photo", photo);
        userPayload.put("phoneNumber", phoneNumber);
        userPayload.put("email", email);
        userPayload.put("username", username);
        userPayload.put("password", password);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("group", group);
        requestBody.put("details", details);
        requestBody.put("userPayload", userPayload);

        // Отправка POST-запроса
        restClient
                .post()
                .uri("/musical-school-api/students")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .toBodilessEntity();
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

    @Override
    public void updateStudent(int studentId,
                              Integer group,
                              String details,
                              String firstName,
                              String lastName,
                              String middleName,
                              LocalDate dateOfBirth,
                              String photo,
                              String phoneNumber,
                              String email) {

        String token = (String) session.getAttribute("token");

        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("firstName", firstName);
        userPayload.put("lastName", lastName);
        userPayload.put("middleName", middleName);
        userPayload.put("dateOfBirth", dateOfBirth);
        userPayload.put("photo", photo);
        userPayload.put("phoneNumber", phoneNumber);
        userPayload.put("email", email);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("group", group);
        requestBody.put("details", details);
        requestBody.put("updateUserPayload", userPayload);

        restClient
                .patch()
                .uri("/musical-school-api/students/student/{studentId}", studentId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .toBodilessEntity();
    }



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
