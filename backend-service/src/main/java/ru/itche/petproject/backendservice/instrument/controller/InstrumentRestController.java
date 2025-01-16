package ru.itche.petproject.backendservice.instrument.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.itche.petproject.backendservice.instrument.controller.payload.NewInstrumentPayload;
import ru.itche.petproject.backendservice.instrument.entity.Instrument;
import ru.itche.petproject.backendservice.instrument.service.InstrumentService;
import ru.itche.petproject.backendservice.subject.entity.Subject;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/musical-school-api/instruments")
@RequiredArgsConstructor
public class InstrumentRestController {

    private final InstrumentService instrumentService;

    @GetMapping
    public Iterable<Instrument> getInstruments() {
        return instrumentService.getAllInstrument();
    }

    @PostMapping
    public ResponseEntity<Instrument> createInstrument(@RequestBody NewInstrumentPayload payload,
                                                       UriComponentsBuilder uriBuilder) {

        Instrument instrument = this.instrumentService.createInstrument(payload.name(), payload.detail());

        return ResponseEntity.created(uriBuilder
                        .replacePath("/musical-school-api/courses/course/{courseId}")
                        .build(Map.of("courseId", instrument.getId())))
                .body(instrument);
    }

    @PostMapping("/{userId:\\d+}")
    private ResponseEntity<?> addInstrumentsToUser(@PathVariable("userId") Integer userId,
                                                  @RequestBody Map<Integer, String> instrumentIds ) {
        this.instrumentService.addInstrumentsToUser(userId, instrumentIds);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/{userId:\\d+}")
    public Iterable<Instrument> findInstrumentsByUser(@PathVariable("userId") Integer userId) {
        return this.instrumentService.getInstrumentsByUser(userId);
    }

    @DeleteMapping("/{instrumentId:\\d+}/{userId:\\d+}")
    public void deleteInstrumentByUser(@PathVariable("instrumentId") Integer instrumentId,
                                       @PathVariable("userId") Integer userId){
        this.instrumentService.deleteInstrumentToUser(userId, instrumentId);
    }
}
