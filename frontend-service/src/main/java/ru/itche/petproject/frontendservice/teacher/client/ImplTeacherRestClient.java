package ru.itche.petproject.frontendservice.teacher.client;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.itche.petproject.frontendservice.teacher.entityRecord.Teacher;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ImplTeacherRestClient implements TeacherRestClient{

    private final RestClient restClient;
    private final HttpSession session;

    private static final ParameterizedTypeReference<List<Teacher>> TEACHER_TYPE_REFERENCE =
            new ParameterizedTypeReference<>(){};

    @Override
    public List<Teacher> getAllTeachers() {
        String token = (String) session.getAttribute("token");

        return restClient
                .get()
                .uri("/musical-school-api/teachers")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(TEACHER_TYPE_REFERENCE);
    }

    @Override
    public Teacher createTeacher(String education,
                              String details,
                              String firstName,
                              String lastName,
                              String middleName,
                              LocalDate dateOfBirth,
                              String photo,
                              String phoneNumber,
                              String email,
                              String username,
                              String password, String passportSeries,
                              String passportNumber, String issuedBy,
                              LocalDate issueDate, String birthCertificateNumber,
                              String city, String street, String home,
                              String flat) {

        String token = (String) session.getAttribute("token");

        Map<String, Object> addressPayload = new HashMap<>();
        addressPayload.put("city", city);
        addressPayload.put("street", street);
        addressPayload.put("home", home);
        addressPayload.put("flat", flat);

        Map<String, Object> cardPayload = new HashMap<>();
        cardPayload.put("issuedBy", issuedBy);
        cardPayload.put("birthCertificateNumber", birthCertificateNumber);
        cardPayload.put("issueDate", issueDate);
        cardPayload.put("passportSeries", passportSeries);
        cardPayload.put("passportNumber", passportNumber);

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
        userPayload.put("addressPayload", addressPayload);
        userPayload.put("cardPayload", cardPayload);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("education", education);
        requestBody.put("details", details);
        requestBody.put("userPayload", userPayload);

        // Отправка POST-запроса
        return restClient
                .post()
                .uri("/musical-school-api/teachers")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(Teacher.class);
    }


    @Override
    public Optional<Teacher> findTeacher(int teacherId) {
        String token = (String) session.getAttribute("token");

        return Optional.ofNullable(restClient
                .get()
                .uri("/musical-school-api/teachers/teacher/{teacherId}", teacherId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(Teacher.class));
    }

    @Override
    public void updateTeacher(int teacherId,
                              String education,
                              String details,
                              String firstName,
                              String lastName,
                              String middleName,
                              LocalDate dateOfBirth,
                              String photo,
                              String phoneNumber, String email , String passportSeries,
                              String passportNumber, String issuedBy,
                              LocalDate issueDate, String birthCertificateNumber,
                              String city, String street, String home,
                              String flat) {

        String token = (String) session.getAttribute("token");

        Map<String, Object> addressPayload = new HashMap<>();
        addressPayload.put("city", city);
        addressPayload.put("street", street);
        addressPayload.put("home", home);
        addressPayload.put("flat", flat);

        Map<String, Object> cardPayload = new HashMap<>();
        cardPayload.put("issuedBy", issuedBy);
        cardPayload.put("birthCertificateNumber", birthCertificateNumber);
        cardPayload.put("issueDate", issueDate);
        cardPayload.put("passportSeries", passportSeries);
        cardPayload.put("passportNumber", passportNumber);

        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("firstName", firstName);
        userPayload.put("lastName", lastName);
        userPayload.put("middleName", middleName);
        userPayload.put("dateOfBirth", dateOfBirth);
        userPayload.put("photo", photo);
        userPayload.put("phoneNumber", phoneNumber);
        userPayload.put("email", email);
        userPayload.put("cardPayload", cardPayload);
        userPayload.put("addressPayload", addressPayload);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("education", education);
        requestBody.put("details", details);
        requestBody.put("userPayload", userPayload);

        System.out.println(requestBody);

        restClient
                .patch()
                .uri("/musical-school-api/teachers/teacher/{teacherId}", teacherId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void deleteTeacher(int teacherId) {
        String token = (String) session.getAttribute("token");

        restClient
                .delete()
                .uri("/musical-school-api/teachers/teacher/{teacherId}", teacherId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .toBodilessEntity();
    }


}
