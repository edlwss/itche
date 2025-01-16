package ru.itche.petproject.frontendservice.instrument.entity;

import ru.itche.petproject.frontendservice.user.entityRecord.User;

public record InstrumentsByUser(Integer id,
                                Instrument instrument,
                                User user,
                                String level) {
}
