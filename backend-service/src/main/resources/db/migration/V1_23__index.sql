CREATE INDEX idx_grade_lesson_id ON musical_school.grade (lesson_id);
CREATE INDEX idx_grade_student_id ON musical_school.grade (student_id);
CREATE INDEX idx_student_group ON musical_school.student ("group");
CREATE INDEX idx_lesson_id ON musical_school.lesson (id);


CREATE INDEX idx_group_id ON musical_school.group (id);
