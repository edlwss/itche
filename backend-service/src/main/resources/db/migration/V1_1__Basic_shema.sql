CREATE TABLE musical_school.course
(
    id               SERIAL PRIMARY KEY,
    title            VARCHAR(255),
    title_curriculum VARCHAR(255)
);

CREATE TABLE musical_school.subject
(
    id             SERIAL PRIMARY KEY,
    title          VARCHAR(255),
    title_syllabus VARCHAR(255),
    update_date    date
);