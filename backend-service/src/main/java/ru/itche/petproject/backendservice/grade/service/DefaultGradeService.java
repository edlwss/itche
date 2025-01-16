package ru.itche.petproject.backendservice.grade.service;

import com.aspose.pdf.BorderInfo;
import com.aspose.pdf.BorderSide;
import com.aspose.pdf.Color;
import com.aspose.pdf.FontStyles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itche.petproject.backendservice.grade.entity.Grade;
import ru.itche.petproject.backendservice.grade.repository.GradeRepository;
import ru.itche.petproject.backendservice.group.service.GroupService;
import ru.itche.petproject.backendservice.lesson.entity.Lesson;
import ru.itche.petproject.backendservice.lesson.service.LessonService;
import ru.itche.petproject.backendservice.student.entity.Student;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;
import com.aspose.pdf.Table;
import com.aspose.pdf.Row;
import com.aspose.pdf.Cell;
import ru.itche.petproject.backendservice.student.service.StudentService;

@Service
@RequiredArgsConstructor
public class DefaultGradeService implements GradeService {

    private final GradeRepository gradeRepository;
    private final GroupService groupService;
    private final LessonService lessonService;
    private final StudentService studentService;

    @Override
    @Transactional
    public Iterable<Grade> getGradeByGroup(Integer groupId, Integer lessonId) {
        Map<String, List<Student>> groupStudentsMap = groupService.getStudentsByGroup(groupId);
        List<Student> students = groupStudentsMap.getOrDefault(
                groupService.getGroup(groupId).orElseThrow(() -> new IllegalArgumentException("Invalid group ID")).getTitle(),
                new ArrayList<>()
        );

        Lesson lesson = lessonService.findLessonId(lessonId);

        List<Grade> existingGrades = (List<Grade>) gradeRepository.findGradesByGroupAndSubject(groupId, lessonId);

        Map<Integer, Grade> gradeMap = existingGrades.stream()
                .collect(Collectors.toMap(grade -> grade.getStudent().getId(), grade -> grade));

        List<Grade> grades = new ArrayList<>();
        for (Student student : students) {
            Grade grade = gradeMap.getOrDefault(student.getId(), createEmptyGrade(student, lesson));
            grades.add(grade);
        }

        return grades;
    }

    private Grade createEmptyGrade(Student student, Lesson lesson) {
        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setLesson(lesson);
        grade.setEstimation(null);
        grade.setPresence(null);
        return grade;
    }

    @Override
    @Transactional
    public Iterable<Grade> createGrades(Integer groupId, Integer lessonId,
                                        Map<String, String> estimation,
                                        Map<String, String> presence) {
        Map<String, List<Student>> groupStudentsMap = groupService.getStudentsByGroup(groupId);
        List<Student> students = groupStudentsMap.getOrDefault(
                groupService.getGroup(groupId).orElseThrow(() -> new IllegalArgumentException("Invalid group ID")).getTitle(),
                new ArrayList<>()
        );

        List<Grade> grades = new ArrayList<>();
        Lesson lesson = lessonService.findLessonId(lessonId);

        for (Student student : students) {
            String studentId = String.valueOf(student.getId());

            String estimationValue = estimation.get(studentId);
            String presenceValue = presence.get(studentId);

            Optional<Grade> existingGradeOpt = gradeRepository.findByStudentAndLesson(student.getId(), lesson.getId());

            Grade grade;
            if (existingGradeOpt.isPresent()) {
                grade = existingGradeOpt.get();
            } else {
                grade = new Grade();
                grade.setStudent(student);
                grade.setLesson(lesson);
            }

            grade.setEstimation(estimationValue != null && !estimationValue.equals("null") ? Integer.valueOf(estimationValue) : null);
            grade.setPresence(presenceValue != null && !presenceValue.equals("null") ? presenceValue : null);

            grades.add(grade);
        }

        return gradeRepository.saveAll(grades);
    }

    @Override
    @Transactional
    public Map<String, List<Number>> getGradeByStudentId(Integer studentId) {
        List<Object[]> result = gradeRepository.findSubjectsAndGradesByStudentId(studentId);

        return result.stream()
                .collect(Collectors.groupingBy(
                        row -> (String) row[0], // Группировка по названию предмета
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                rows -> {
                                    // Расчет средней оценки
                                    double avgGrade = rows.stream()
                                            .map(row -> (Number) row[1]) // Извлечение оценки
                                            .filter(Objects::nonNull)    // Игнорирование null
                                            .mapToDouble(Number::doubleValue)
                                            .average()
                                            .orElse(0.0);

                                    // Подсчет посещенных занятий
                                    int attended = (int) rows.stream()
                                            .filter(row -> "Присутствовал".equalsIgnoreCase((String) row[2]))
                                            .count();

                                    // Подсчет пропущенных занятий
                                    int missed = (int) rows.stream()
                                            .filter(row -> "Отсутствовал".equalsIgnoreCase((String) row[2]))
                                            .count();

                                    // Возврат списка из средней оценки, посещенных и пропущенных занятий
                                    return List.of(
                                            Math.round(avgGrade * 10) / 10.0, // Средняя оценка, округленная до 1 знака
                                            attended,
                                            missed
                                    );
                                }
                        )
                ));
    }



    @Override
    @Transactional
    public byte[] generatePdf(Integer studentId, Map<String, List<Number>> grades) {
        Document document = new Document();

        Page page = document.getPages().add();

        Student student = studentService.findStudent(studentId).orElseThrow();
        String studentName = student.getUser().getFirstName() + " " +
                student.getUser().getLastName();

        TextFragment title = new TextFragment("Табель ученика: " + studentName);
        title.getTextState().setFontSize(16);
        title.getTextState().setFontStyle(FontStyles.Bold);
        title.getTextState().setForegroundColor(Color.getBlack());
        page.getParagraphs().add(title);

        page.getParagraphs().add(new TextFragment("\n"));

        Table table = new Table();
        table.setColumnWidths("150 75 75 75"); // Set column widths for four columns
        table.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack())); // Set the table border

        Row headerRow = table.getRows().add();
        headerRow.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack())); // Border for the header row

        String[] headers = {"Предмет", "Средняя оценка", "Посещено", "Пропущено"};
        for (String header : headers) {
            Cell headerCell = new Cell();
            headerCell.getParagraphs().add(new TextFragment(header));
            headerCell.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack())); // Border for each header cell
            headerRow.getCells().add(headerCell);
        }

        for (Map.Entry<String, List<Number>> entry : grades.entrySet()) {
            Row row = table.getRows().add();
            row.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack())); // Border for each row

            Cell subjectCell = new Cell();
            subjectCell.getParagraphs().add(new TextFragment(entry.getKey()));
            subjectCell.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));
            row.getCells().add(subjectCell);

            Cell gradeCell = new Cell();
            gradeCell.getParagraphs().add(new TextFragment(entry.getValue().get(0).toString()));
            gradeCell.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));
            row.getCells().add(gradeCell);

            Cell attendedCell = new Cell();
            attendedCell.getParagraphs().add(new TextFragment(entry.getValue().get(1).toString()));
            attendedCell.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));
            row.getCells().add(attendedCell);

            Cell missedCell = new Cell();
            missedCell.getParagraphs().add(new TextFragment(entry.getValue().get(2).toString()));
            missedCell.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));
            row.getCells().add(missedCell);
        }

        page.getParagraphs().add(table);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);

        return outputStream.toByteArray();
    }


}

