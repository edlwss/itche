CREATE TABLE musical_school.course_subjects
(
    id      SERIAL PRIMARY KEY,
    course  INTEGER,
    subject INTEGER,
    details VARCHAR(255)
);

ALTER TABLE musical_school.course_subjects
    ADD CONSTRAINT FK_COURSE_SUBJECTS_ON_COURSE FOREIGN KEY (course) REFERENCES musical_school.course (id);

ALTER TABLE musical_school.course_subjects
    ADD CONSTRAINT FK_COURSE_SUBJECTS_ON_SUBJECT FOREIGN KEY (subject) REFERENCES musical_school.subject (id);