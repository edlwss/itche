package ru.itche.petproject.backendservice.grade.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.itche.petproject.backendservice.lesson.entity.Lesson;
import ru.itche.petproject.backendservice.student.entity.Student;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(schema = "musical_school", name = "grade")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grade_generator")
    @SequenceGenerator(name = "grade_generator", sequenceName = "musical_school.grade_id_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "lessonId")
    @NotNull
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "studentId")
    @NotNull
    private Student student;

    @Min(1)
    @Max(5)
    @Column(name = "estimation")
    Integer estimation;

    @Column(name = "presence")
    @Pattern(regexp = "^(присутствовал|отсутствовал)$")
    String presence;
}
