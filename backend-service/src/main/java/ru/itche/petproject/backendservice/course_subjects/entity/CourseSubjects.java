package ru.itche.petproject.backendservice.course_subjects.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.itche.petproject.backendservice.course.entity.Course;
import ru.itche.petproject.backendservice.subject.entity.Subject;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(schema = "musical_school", name = "course_subjects")
public class CourseSubjects {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_subjects_generator")
    @SequenceGenerator(name = "course_subjects_generator", sequenceName = "musical_school.course_subjects_id_seq", allocationSize = 1)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "course")
    Course course;

    @ManyToOne
    @JoinColumn(name = "subject")
    Subject subject;

    @Column(name = "details")
    String details;

}
