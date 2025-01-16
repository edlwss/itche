package ru.itche.petproject.backendservice.instrument.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.itche.petproject.backendservice.user.entity.User;
import ru.itche.petproject.backendservice.instrument.entity.Instrument;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(schema = "musical_school", name = "instruments_user")
public class InstrumentsByUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "instruments_user_generator")
    @SequenceGenerator(name = "instruments_user_generator", sequenceName = "musical_school.instruments_user_id_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instrument_id")
    Instrument instrument;

    @Column(name = "level")
    String level;
}
