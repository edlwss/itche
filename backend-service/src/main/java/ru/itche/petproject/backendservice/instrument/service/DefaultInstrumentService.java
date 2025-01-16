package ru.itche.petproject.backendservice.instrument.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itche.petproject.backendservice.instrument.entity.Instrument;
import ru.itche.petproject.backendservice.instrument.entity.InstrumentsByUser;
import ru.itche.petproject.backendservice.instrument.repository.InstrumentRepository;
import ru.itche.petproject.backendservice.instrument.repository.InstrumentsByUserRepository;
import ru.itche.petproject.backendservice.user.entity.User;
import ru.itche.petproject.backendservice.user.repository.UserRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DefaultInstrumentService implements InstrumentService {

    private final InstrumentRepository instrumentRepository;
    private final UserRepository userRepository;
    private final InstrumentsByUserRepository instrumentsByUserRepository;

    @Override
    public Iterable<Instrument> getAllInstrument() {
        return instrumentRepository.findAll();
    }

    @Override
    @Transactional
    public Instrument createInstrument(String name, String detail) {
        return instrumentRepository.save(new Instrument(null, name, detail));
    }

    @Override
    @Transactional
    public void addInstrumentsToUser(Integer userId, Map<Integer, String> instrumentsIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<InstrumentsByUser> instrumentsByUser = instrumentsIds.entrySet().stream()
                .map(entry -> {
                    Integer instrumentId = entry.getKey();
                    String level = entry.getValue();

                    Instrument instrument = instrumentRepository.findById(instrumentId)
                            .orElseThrow(() -> new IllegalArgumentException("Instrument not found for ID: " + instrumentId));

                    return new InstrumentsByUser(null, user, instrument, level);
                })
                .toList();

        this.instrumentsByUserRepository.saveAll(instrumentsByUser);
    }

    @Override
    @Transactional
    public Iterable<Instrument> getInstrumentsByUser(Integer userId) {
        List <Integer> inst = this.instrumentsByUserRepository.getInstrumentIdsByUser(userId);
        return instrumentRepository.findAllById(inst);
    }

    @Override
    @Transactional
    public void deleteInstrumentToUser(int instrumentId, Integer userId) {
        this.instrumentsByUserRepository.deleteByInstrumentId(instrumentId, userId);
    }
}
