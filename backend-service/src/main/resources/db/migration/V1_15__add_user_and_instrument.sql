CREATE TABLE instruments_user
(
    id            SERIAL PRIMARY KEY,
    user_id       INTEGER,
    instrument_id INTEGER,
    level         VARCHAR(255)
);

ALTER TABLE instruments_user
    ADD CONSTRAINT FK_GROUP_ON_COURSE FOREIGN KEY (user_id) REFERENCES musical_school."user" (id);

ALTER TABLE instruments_user
    ADD CONSTRAINT FK_STUDENT_ON_GROUP FOREIGN KEY ("instrument_id") REFERENCES musical_school.instrument (id);