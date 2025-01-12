CREATE TABLE lesson
(
    id          SERIAL PRIMARY KEY,
    "group"     INTEGER,
    subject     INTEGER,
    time_lesson time WITHOUT TIME ZONE,
    date_lesson date
);

ALTER TABLE lesson
    ADD CONSTRAINT FK_GROUP_ON_LESSON FOREIGN KEY ("group") REFERENCES musical_school.group (id);

ALTER TABLE lesson
    ADD CONSTRAINT FK_SUBJECT_ON_LESSON FOREIGN KEY (subject) REFERENCES musical_school.subject (id);

DROP TABLE teacher_subjects CASCADE;

