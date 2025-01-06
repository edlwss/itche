package ru.itche.petproject.backendservice.teacher_subject.entity;

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
import ru.itche.petproject.backendservice.course.entity.Course;
import ru.itche.petproject.backendservice.subject.entity.Subject;
import ru.itche.petproject.backendservice.teacher.entity.Teacher;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(schema = "musical_school", name = "teacher_subjects")
public class TeacherSubjects {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_subjects_generator")
    @SequenceGenerator(name = "teacher_subjects_generator", sequenceName = "musical_school.teacher_subjects_id_seq", allocationSize = 1)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "subject")
    Subject subject;

    @ManyToOne
    @JoinColumn(name = "teacher")
    Teacher teacher;

    @Column(name = "details")
    String details;

}
