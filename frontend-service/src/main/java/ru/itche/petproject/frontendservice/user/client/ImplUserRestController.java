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
import ru.itche.petproject.frontendservice.user.entityRecord.UserToken;


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
    public String getUserRoleFromServer() {
        String token = (String) session.getAttribute("token");

        if (token == null) {
            return "ROLE_ANONYMOUS"; // Если токен не найден, возвращаем роль по умолчанию
        }

        return restClient
                .get()
                .uri("/role")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(String.class);
    }

    //методы админа


}
