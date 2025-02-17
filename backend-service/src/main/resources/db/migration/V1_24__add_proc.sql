CREATE OR REPLACE PROCEDURE addCourse(
    p_title VARCHAR,
    p_title_curriculum VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN
INSERT INTO musical_school.course (title, title_curriculum, update_course)
VALUES (p_title, p_title_curriculum, CURRENT_DATE);
END;
$$;