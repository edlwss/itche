package ru.itche.petproject.frontendservice.instrument.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.itche.petproject.frontendservice.instrument.client.payload.NewInstrumentPayload;
import ru.itche.petproject.frontendservice.instrument.entity.Instrument;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ImplInstrumentClient implements InstrumentRestClient {

    private final RestClient restClient;
    private final HttpSession session;

    private static final ParameterizedTypeReference<List<Instrument>> INSTRUMENTS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {};

    @Override
    public List<Instrument> getInstruments() {

        String token = (String) session.getAttribute("token");
        return restClient
                .get()
                .uri("/musical-school-api/instruments")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(INSTRUMENTS_TYPE_REFERENCE);
    }

    @Override
    public Instrument createInstrument(String name, String detail) {
        String token = (String) session.getAttribute("token");

        // Формирование тела запроса
        NewInstrumentPayload payload = new NewInstrumentPayload(name, detail);
        return restClient
                .post()
                .uri("/musical-school-api/instruments")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(payload)
                .retrieve()
                .body(Instrument.class);
    }

    @Override
    public void addInstrumentsToUser(Integer userId, Map<Integer, String> instrumentIds) {
        String token = (String) session.getAttribute("token");
        restClient
                .post()
                .uri("/musical-school-api/instruments/{userId}", userId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(instrumentIds)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public List<Instrument> findInstrumentsByUser(Integer userId) {
        String token = (String) session.getAttribute("token");
        return restClient
                .get()
                .uri("/musical-school-api/instruments/{userId}", userId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(INSTRUMENTS_TYPE_REFERENCE);
    }

    @Override
    public void deleteInstrumentByUser(Integer instrumentId, Integer userId) {
        String token = (String) session.getAttribute("token");
        restClient
                .delete()
                .uri("/musical-school-api/instruments/{instrumentId}/{userId}", instrumentId, userId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .toBodilessEntity();
    }
}
