package ru.itche.petproject.frontendservice.user.client;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import ru.itche.petproject.frontendservice.teacher.entityRecord.Teacher;
import ru.itche.petproject.frontendservice.user.entityRecord.User;
import ru.itche.petproject.frontendservice.user.entityRecord.UserToken;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class ImplUserRestController implements UserRestClient{

    private final RestClient restClient;
    private final HttpSession session;

    //Базовые методы для всех юзеров
    public UserToken authenticate(String username, String password) {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", username);
        body.add("password", password);

        return restClient
                .post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // Указываем тип контента
                .body(body) // Передаем данные формы
                .retrieve()
                .body(UserToken.class); // Преобразуем ответ в объект

    }
    @Override
    public Optional<User> findAdmin(int adminId) {
        String token = (String) session.getAttribute("token");

        return Optional.ofNullable(restClient
                .get()
                .uri("/musical-school-api/admin/{teacherId}", adminId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(User.class));
    }

}
