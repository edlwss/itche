package ru.itche.petproject.frontendservice.user.client;

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

    public UserToken authenticate(String username, String password) {
        // Создаем объект с данными для аутентификации в формате x-www-form-urlencoded
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


    public boolean validateToken(String token) {
        ResponseEntity<Void> response = restClient
                .get()
                .uri("/musical-school-api/validate-token")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .toBodilessEntity();
        return response.getStatusCode().is2xxSuccessful();
    }
}
