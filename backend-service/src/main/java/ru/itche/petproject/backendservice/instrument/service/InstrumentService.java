package ru.itche.petproject.backendservice.instrument.service;

import ru.itche.petproject.backendservice.instrument.entity.Instrument;

import java.util.List;
import java.util.Map;

public interface InstrumentService {


    Iterable<Instrument> getAllInstrument();

    Instrument createInstrument(String name, String detail);

    void addInstrumentsToUser(Integer userId, Map<Integer, String> instrumentIds);

    Iterable<Instrument> getInstrumentsByUser(Integer userId);

    void deleteInstrumentToUser(int instrumentId, Integer userId);
}
