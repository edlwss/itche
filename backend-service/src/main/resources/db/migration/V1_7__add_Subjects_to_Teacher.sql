CREATE TABLE teacher_subjects
(
    id      SERIAL PRIMARY KEY,
    subject INTEGER,
    teacher INTEGER,
    details VARCHAR(255)
);

