ALTER TABLE musical_school.lesson
DROP CONSTRAINT fk_subject_on_lesson;

ALTER TABLE musical_school.lesson
    ADD CONSTRAINT fk_subject_on_lesson
        FOREIGN KEY (subject) REFERENCES musical_school.subject(id)
            ON DELETE CASCADE;

ALTER TABLE musical_school.lesson
DROP CONSTRAINT fk_group_on_lesson;

ALTER TABLE musical_school.lesson
    ADD CONSTRAINT fk_group_on_lesson
        FOREIGN KEY (group_id) REFERENCES musical_school."group"(id)
            ON DELETE CASCADE;

ALTER TABLE musical_school.grade
DROP CONSTRAINT fk_lesson;

ALTER TABLE musical_school.grade
    ADD CONSTRAINT fk_lesson
        FOREIGN KEY (lesson_id) REFERENCES musical_school.lesson(id)
            ON DELETE CASCADE;

ALTER TABLE musical_school.grade
    DROP CONSTRAINT fk_student;

ALTER TABLE musical_school.grade
    ADD CONSTRAINT fk_student
        FOREIGN KEY (student_id) REFERENCES musical_school.student(id)
            ON DELETE CASCADE;
