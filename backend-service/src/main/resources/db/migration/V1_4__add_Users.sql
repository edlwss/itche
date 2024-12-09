CREATE TABLE role
(
    id        SERIAL PRIMARY KEY ,
    name_role VARCHAR(50)
);

INSERT INTO musical_school.role (name_role) VALUES
                                                    ('STUDENT'),
                                                    ('TEACHER'),
                                                    ('ADMIN');

CREATE TABLE "user"
(
    id            SERIAL PRIMARY KEY,
    last_name     VARCHAR(255),
    name          VARCHAR(255),
    middle_name   VARCHAR(255),
    data_of_birth date,
    photo         VARCHAR(255),
    phone_number  VARCHAR(255),
    email         VARCHAR(255),
    username      VARCHAR(255),
    password      VARCHAR(255),
    role          INTEGER
);