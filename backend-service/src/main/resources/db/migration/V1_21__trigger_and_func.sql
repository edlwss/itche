CREATE OR REPLACE FUNCTION update_subject_date()
RETURNS TRIGGER AS $$
BEGIN
    NEW.update_date = CURRENT_DATE;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trigger_update_subject_date
    BEFORE INSERT OR UPDATE ON musical_school.subject
                         FOR EACH ROW
                         EXECUTE FUNCTION update_subject_date();

