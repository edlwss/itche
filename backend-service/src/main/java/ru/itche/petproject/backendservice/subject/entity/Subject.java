package ru.itche.petproject.backendservice.subject.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.itche.petproject.backendservice.teacher.entity.Teacher;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "musical_school", name = "subject")

public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_generator")
    @SequenceGenerator(name = "subject_generator", sequenceName = "musical_school.subject_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "title_syllabus")
    private String titleSyllabus;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @PrePersist
    public void prePersist() {
        this.updateDate = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = LocalDate.now();
    }
}
