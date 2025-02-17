package ru.itche.petproject.backendservice.group.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.itche.petproject.backendservice.course.entity.Course;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(schema = "musical_school", name = "group")

public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_generator")
    @SequenceGenerator(name = "group_generator", sequenceName = "musical_school.group_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "title")
    @NotNull
    private String title;

    @ManyToOne
    @JoinColumn(name = "course")
    @NotNull
    private Course course;

    @Column(name = "start_education")
    @NotNull
    private LocalDate startEducation;

    @Column(name = "end_education")
    @NotNull
    private LocalDate endEducation;

}
