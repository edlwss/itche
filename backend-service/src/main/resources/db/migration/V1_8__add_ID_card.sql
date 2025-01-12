CREATE TABLE musical_school.idcard
(
    id                       SERIAL PRIMARY KEY ,
    passport_series          VARCHAR(255),
    passport_number          VARCHAR(255),
    issued_by                VARCHAR(255),
    birth_certificate_number VARCHAR(255),
    issue_date               TIMESTAMP WITHOUT TIME ZONE
);

ALTER TABLE musical_school."user"
    ADD id_card INTEGER;

ALTER TABLE musical_school.user
    ADD CONSTRAINT FK_USER_ON_IDCARD FOREIGN KEY (id_card) REFERENCES musical_school.idcard (id);
