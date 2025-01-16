ALTER TABLE musical_school."group"
    ADD CONSTRAINT fk_course_id
        FOREIGN KEY (course) REFERENCES musical_school.course(id)
            ON DELETE CASCADE;