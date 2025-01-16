package ru.itche.petproject.backendservice.subject.service;

import com.aspose.pdf.BorderInfo;
import com.aspose.pdf.BorderSide;
import com.aspose.pdf.Color;
import com.aspose.pdf.FontStyles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itche.petproject.backendservice.subject.entity.Subject;
import ru.itche.petproject.backendservice.subject.repository.SubjectRepository;
import ru.itche.petproject.backendservice.teacher.entity.Teacher;
import ru.itche.petproject.backendservice.teacher.repository.TeacherRepository;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;
import com.aspose.pdf.Table;
import com.aspose.pdf.Row;
import com.aspose.pdf.Cell;

@Service
@RequiredArgsConstructor
public class DefaultSubjectService implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public Iterable<Subject> getAllSubjects() {
        return this.subjectRepository.findAll();
    }

    @Override
    public Optional<Subject> findSubject(Integer subjectId) {
        return this.subjectRepository.findById(subjectId);
    }

    @Override
    @Transactional
    public void updateSubject(Integer subjectId, String title, String titleSyllabus, Integer idTeacher) {
        this.subjectRepository.findById(subjectId).ifPresent(subject -> {
            subject.setTitle(title);
            subject.setTitleSyllabus(titleSyllabus);
            subject.setTeacher(teacherRepository.findById(idTeacher).orElse(null));
        });
    }

    @Override
    @Transactional
    public void deleteSubject(Integer subjectId) {
        this.subjectRepository.deleteById(subjectId);
    }

    @Override
    @Transactional
    public Subject createSubject(String title, String titleSyllabus, Integer teacherId) {

        Teacher teacher = this.teacherRepository.findById(teacherId).orElse(null);
        return this.subjectRepository.save(new Subject(null, title, titleSyllabus, null, teacher));
    }

    @Override
    public Iterable<Subject> getSubjectsByTeacher(Integer teacherId) {
        return subjectRepository.findSubjectsByTeacherId(teacherId);
    }

    @Override
    @Transactional
    public void chageTeacher(Integer teacherId, List<Integer> subjectsId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Учитель с ID " + teacherId + " не найден"));

        List<Subject> subjects = (List<Subject>) subjectRepository.findAllById(subjectsId);

        subjects.forEach(subject -> subject.setTeacher(teacher));
        subjectRepository.saveAll(subjects);
    }

    @Override
    @Transactional
    public Map<Integer, List<Subject>> getSubjectsGroupedByTeacher() {
        List<Object[]> rawData = subjectRepository.findTeacherAndSubjectIds();

        // Преобразуем Iterable в Stream
        Map<Integer, Subject> allSubjects = new HashMap<>();
        subjectRepository.findAll().forEach(subject -> allSubjects.put(subject.getId(), subject));

        // Группируем по teacher_id
        Map<Integer, List<Subject>> result = new HashMap<>();

        for (Object[] row : rawData) {
            Integer teacherId = (Integer) row[0];
            Integer subjectId = (Integer) row[1];

            Subject subject = allSubjects.get(subjectId);

            result.computeIfAbsent(teacherId, k -> new ArrayList<>()).add(subject);
        }

        return result;
    }

    @Override
    @Transactional
    public byte[] generatePdf() {
        // Получаем сгруппированные предметы по преподавателям
        Map<Integer, List<Subject>> subjectsByTeacher = getSubjectsGroupedByTeacher();

        // Создаем новый PDF-документ
        Document document = new Document();
        Page page = document.getPages().add();

        // Добавляем заголовок
        TextFragment title = new TextFragment("Список преподавателей и их дисциплин");
        title.getTextState().setFontSize(16);
        title.getTextState().setFontStyle(FontStyles.Bold);
        title.getTextState().setForegroundColor(Color.getBlack());
        page.getParagraphs().add(title);

        // Добавляем отступ после заголовка
        page.getParagraphs().add(new TextFragment("\n"));

        // Создаем таблицу
        Table table = new Table();
        table.setColumnWidths("200 300");
        table.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));

        // Добавляем заголовки колонок
        Row headerRow = table.getRows().add();
        headerRow.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));

        Cell teacherHeader = new Cell();
        teacherHeader.getParagraphs().add(new TextFragment("Преподаватель"));
        teacherHeader.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));
        headerRow.getCells().add(teacherHeader);

        Cell subjectHeader = new Cell();
        subjectHeader.getParagraphs().add(new TextFragment("Дисциплины"));
        subjectHeader.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));
        headerRow.getCells().add(subjectHeader);

        // Добавляем данные о преподавателях и их предметах
        for (Map.Entry<Integer, List<Subject>> entry : subjectsByTeacher.entrySet()) {
            Integer teacherId = entry.getKey();
            List<Subject> subjects = entry.getValue();

            // Получаем имя преподавателя по его ID
            String teacherName = teacherRepository.findById(teacherId)
                    .map(teacher -> teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName())
                    .orElse("Неизвестный преподаватель");

            // Создаем строку таблицы
            Row row = table.getRows().add();
            row.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));

            // Первая колонка — имя преподавателя
            Cell teacherCell = new Cell();
            teacherCell.getParagraphs().add(new TextFragment(teacherName));
            teacherCell.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));
            row.getCells().add(teacherCell);

            // Вторая колонка — предметы
            Cell subjectCell = new Cell();
            String subjectNames = subjects.stream()
                    .map(Subject::getTitle)
                    .reduce((s1, s2) -> s1 + ", " + s2)
                    .orElse("Нет дисциплин");
            subjectCell.getParagraphs().add(new TextFragment(subjectNames));
            subjectCell.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));
            row.getCells().add(subjectCell);
        }

        // Добавляем таблицу на страницу
        page.getParagraphs().add(table);

        // Сохраняем документ в байтовый массив
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        return outputStream.toByteArray();
    }

}
