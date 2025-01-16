package ru.itche.petproject.backendservice.instrument.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.itche.petproject.backendservice.instrument.entity.Instrument;
import ru.itche.petproject.backendservice.instrument.entity.InstrumentsByUser;

import java.util.List;

public interface InstrumentsByUserRepository extends CrudRepository<InstrumentsByUser, Integer> {

    @Query(value = "SELECT i.id " +
            "FROM musical_school.instrument i " +
            "JOIN musical_school.instruments_user iu ON i.id = iu.instrument_id " +
            "WHERE iu.user_id = :userId", nativeQuery = true)
    List<Integer> getInstrumentIdsByUser(@Param("userId") Integer userId);

    @Modifying
    @Query(value = """
            DELETE FROM musical_school.instruments_user iu
            WHERE iu.instrument_id = :instrumentId AND iu.user_id = :userId""", nativeQuery = true)
    void deleteByInstrumentId(@Param("instrumentId") int instrumentId,
                                      @Param("userId") int userId);

}
