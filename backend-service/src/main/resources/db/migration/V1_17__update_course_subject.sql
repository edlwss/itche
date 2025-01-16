-- Для внешнего ключа на course в таблице course_subjects
ALTER TABLE musical_school.course_subjects
DROP CONSTRAINT fk_course_subjects_on_course;

ALTER TABLE musical_school.course_subjects
    ADD CONSTRAINT fk_course_subjects_on_course
        FOREIGN KEY (course) REFERENCES musical_school.course(id)
            ON DELETE CASCADE;

-- Для внешнего ключа на subject в таблице course_subjects
ALTER TABLE musical_school.course_subjects
DROP CONSTRAINT fk_course_subjects_on_subject;

ALTER TABLE musical_school.course_subjects
    ADD CONSTRAINT fk_course_subjects_on_subject
        FOREIGN KEY (subject) REFERENCES musical_school.subject(id)
            ON DELETE CASCADE;
