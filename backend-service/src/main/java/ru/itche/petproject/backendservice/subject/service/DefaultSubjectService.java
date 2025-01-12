package ru.itche.petproject.backendservice.subject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itche.petproject.backendservice.subject.entity.Subject;
import ru.itche.petproject.backendservice.subject.repository.SubjectRepository;
import ru.itche.petproject.backendservice.teacher.entity.Teacher;
import ru.itche.petproject.backendservice.teacher.repository.TeacherRepository;

import java.util.List;
import java.util.Optional;
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

}
