CREATE TABLE grade
(
    id         SERIAL PRIMARY KEY,
    lesson_id  INTEGER,
    student_id INTEGER,
    estimation INTEGER,
    presence   VARCHAR(255),
    CONSTRAINT fk_lesson FOREIGN KEY (lesson_id) REFERENCES musical_school.lesson (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES musical_school.student (id) ON DELETE CASCADE ON UPDATE CASCADE
);
