package ru.itche.petproject.backendservice.instrument.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itche.petproject.backendservice.instrument.entity.Instrument;

@Repository
public interface InstrumentRepository extends CrudRepository<Instrument, Integer> {


}
