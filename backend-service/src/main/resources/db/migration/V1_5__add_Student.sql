CREATE TABLE "group"
(
    id              SERIAL PRIMARY KEY ,
    title           VARCHAR(255),
    course          INTEGER,
    start_education TIMESTAMP WITHOUT TIME ZONE,
    end_education   TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE student
(
    id      SERIAL PRIMARY KEY ,
    "user"  INTEGER,
    "group" INTEGER,
    details VARCHAR(255)
);
