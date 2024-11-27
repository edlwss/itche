package ru.itche.petproject.backendservice.subject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itche.petproject.backendservice.subject.entity.Subject;
import ru.itche.petproject.backendservice.subject.repository.SubjectRepository;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class DefaultSubjectService implements SubjectService {

    private final SubjectRepository subjectRepository;

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
    public void updateSubject(Integer subjectId, String title, String titleSyllabus) {
        this.subjectRepository.findById(subjectId).ifPresent(subject -> {
            subject.setTitle(title);
            subject.setTitleSyllabus(titleSyllabus);
        });
    }

    @Override
    @Transactional
    public void deleteSubject(Integer subjectId) {
        this.subjectRepository.deleteById(subjectId);
    }

    @Override
    @Transactional
    public Subject createSubject(String title, String titleSyllabus) {
        return this.subjectRepository.save(new Subject(null, title, titleSyllabus, null));
    }
}
