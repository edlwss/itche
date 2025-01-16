CREATE TABLE adress
(
    id     SERIAL PRIMARY KEY,
    city   VARCHAR(255),
    street VARCHAR(255),
    home   VARCHAR(255),
    flat   VARCHAR(255)
);

ALTER TABLE musical_school."user"
    ADD address INTEGER;

ALTER TABLE musical_school."user"
    ADD CONSTRAINT FK_USER_ON_ADDRESS FOREIGN KEY (address) REFERENCES adress (id);

ALTER TABLE musical_school."user"
    ADD CONSTRAINT FK_USER_ON_ROLE FOREIGN KEY (role) REFERENCES role (id);





