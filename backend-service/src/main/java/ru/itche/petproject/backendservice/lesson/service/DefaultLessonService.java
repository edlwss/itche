package ru.itche.petproject.backendservice.lesson.service;

import com.aspose.pdf.BorderInfo;
import com.aspose.pdf.BorderSide;
import com.aspose.pdf.Color;
import com.aspose.pdf.FontStyles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itche.petproject.backendservice.group.entity.Group;
import ru.itche.petproject.backendservice.group.repository.GroupRepository;
import ru.itche.petproject.backendservice.lesson.entity.Lesson;
import ru.itche.petproject.backendservice.lesson.repository.LessonRepository;
import ru.itche.petproject.backendservice.subject.entity.Subject;
import ru.itche.petproject.backendservice.subject.repository.SubjectRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.ByteArrayOutputStream;
import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;
import com.aspose.pdf.Table;
import com.aspose.pdf.Row;
import com.aspose.pdf.Cell;

@Service
@RequiredArgsConstructor
public class DefaultLessonService implements LessonService {

    private final LessonRepository lessonRepository;
    private final SubjectRepository subjectRepository;
    private final GroupRepository groupRepository;

    @Override
    @Transactional
    public Lesson createLesson(Integer groupId, Integer subjectId, LocalTime timeLesson,
                               LocalDate dateLesson) {

        Group group = groupRepository.findById(groupId).orElse(null);
        Subject subject = subjectRepository.findById(subjectId).orElse(null);

        return this.lessonRepository.save(new Lesson(null, group, subject, timeLesson, dateLesson));
    }


    @Override
    @Transactional
    public Map<LocalDate, List<Lesson>> getCalendarFormattedSchedule(Integer groupId, int year, int month) {
        // Получаем список уроков за указанный месяц
        List<Lesson> lessons = lessonRepository.findLessonsByGroupAndMonth(groupId, month, year);

        // Группируем уроки по дате
        Map<LocalDate, List<Lesson>> groupedLessons = lessons.stream()
                .collect(Collectors.groupingBy(Lesson::getDateLesson, TreeMap::new, Collectors.toList()));

        // Заполняем пропущенные даты пустыми списками
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            groupedLessons.putIfAbsent(date, Collections.emptyList());
        }

        return groupedLessons;
    }

    @Override
    public Lesson findLessonId(Integer lessonId) {
        return lessonRepository.findById(lessonId).orElse(null);
    }


    @Override
    @Transactional
    public void deleteLesson(Integer id) {
        lessonRepository.deleteById(id);
    }

    @Override
    @Transactional
    public byte[] generateSchedulePdf(Integer groupId, int year, int month) {
        // Получаем расписание уроков
        Map<LocalDate, List<Lesson>> schedule = getCalendarFormattedSchedule(groupId, year, month);

        String groupName = groupRepository.findById(groupId).orElseThrow().getTitle();
        // Создание нового документа
        Document document = new Document();
        Page page = document.getPages().add();

        // Добавление заголовка
        TextFragment title = new TextFragment("Расписание занятий для группы: " + groupName);
        title.getTextState().setFontSize(16);
        title.getTextState().setFontStyle(FontStyles.Bold);
        page.getParagraphs().add(title);

        // Добавление подзаголовка с годом и месяцем
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("LLLL");
        String monthName = monthFormatter.format(LocalDate.of(year, month, 1));
        TextFragment subtitle = new TextFragment("Год: " + year + ", Месяц: " + monthName);
        subtitle.getTextState().setFontSize(12);
        page.getParagraphs().add(subtitle);

        // Добавление отступа после заголовка
        page.getParagraphs().add(new TextFragment("\n"));

        // Создание таблицы
        Table table = new Table();
        table.setColumnWidths("100 400"); // Первая колонка для даты, вторая для уроков
        table.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));

        // Добавление заголовков таблицы
        Row headerRow = table.getRows().add();
        headerRow.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));

        Cell dateHeader = new Cell();
        dateHeader.getParagraphs().add(new TextFragment("Дата"));
        dateHeader.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));
        headerRow.getCells().add(dateHeader);

        Cell lessonsHeader = new Cell();
        lessonsHeader.getParagraphs().add(new TextFragment("Занятия"));
        lessonsHeader.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));
        headerRow.getCells().add(lessonsHeader);

        // Добавление данных в таблицу
        schedule.forEach((date, lessons) -> {
            Row row = table.getRows().add();
            row.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));

            // Колонка с датой
            Cell dateCell = new Cell();
            dateCell.getParagraphs().add(new TextFragment(date.format(DateTimeFormatter.ofPattern("d MMMM"))));
            dateCell.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));
            row.getCells().add(dateCell);

            // Колонка с уроками
            Cell lessonsCell = new Cell();
            StringBuilder lessonsText = new StringBuilder();
            for (Lesson lesson : lessons) {
                lessonsText.append(lesson.getTimeLesson())
                        .append(" - ")
                        .append(lesson.getSubject().getTitle())
                        .append("\n");
            }
            if (lessonsText.length() == 0) {
                lessonsText.append("Нет занятий");
            }
            lessonsCell.getParagraphs().add(new TextFragment(lessonsText.toString().trim()));
            lessonsCell.setBorder(new BorderInfo(BorderSide.All, 0.5f, Color.getBlack()));
            row.getCells().add(lessonsCell);
        });

        // Добавление таблицы на страницу
        page.getParagraphs().add(table);

        // Сохранение документа в байтовый массив
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        return outputStream.toByteArray();
    }

}
