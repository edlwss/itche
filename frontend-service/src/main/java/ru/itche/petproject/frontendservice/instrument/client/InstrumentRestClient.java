package ru.itche.petproject.frontendservice.instrument.client;

import ru.itche.petproject.frontendservice.instrument.entity.Instrument;

import java.util.List;
import java.util.Map;

public interface InstrumentRestClient {

    List<Instrument> getInstruments();

    Instrument createInstrument(String name, String detail);

    void addInstrumentsToUser(Integer userId, Map<Integer, String> instrumentIds);

    List<Instrument> findInstrumentsByUser(Integer userId);

    void deleteInstrumentByUser(Integer instrumentId, Integer userId);
}

