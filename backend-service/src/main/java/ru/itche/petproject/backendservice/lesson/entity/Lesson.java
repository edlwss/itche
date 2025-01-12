package ru.itche.petproject.backendservice.lesson.entity;

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
import ru.itche.petproject.backendservice.group.entity.Group;
import ru.itche.petproject.backendservice.subject.entity.Subject;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(schema = "musical_school", name = "lesson")

public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_generator")
    @SequenceGenerator(name = "lesson_generator", sequenceName = "musical_school.lesson_id_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne()
    @JoinColumn(name = "subject")
    private Subject subject;

    @Column(name = "time_lesson")
    private LocalTime timeLesson;

    @Column(name = "date_lesson")
    private LocalDate dateLesson;
}
