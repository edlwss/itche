package ru.itche.petproject.backendservice.student.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itche.petproject.backendservice.group.entity.Group;
import ru.itche.petproject.backendservice.group.repository.GroupRepository;
import ru.itche.petproject.backendservice.student.entity.Student;
import ru.itche.petproject.backendservice.student.repository.StudentRepository;
import ru.itche.petproject.backendservice.user.entity.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultStudentService implements StudentService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    @Override
    public Iterable<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> findStudent(int studentId) {
        return studentRepository.findById(studentId);
    }

    @Override
    @Transactional
    public Student createStudent(User user, Integer groupId, String details) {
        Group group = groupRepository.findById(groupId).orElse(null);
        return studentRepository.save(new Student(null, user, group, details));
    }

    @Override
    @Transactional
    public void updateStudent(int studentId, Integer groupId, String details) {
        Group group = groupRepository.findById(groupId).orElse(null);
        this.studentRepository.findById(studentId)
                .ifPresent(student -> {
                    student.setGroup(group);
                    student.setDetails(details);
                });
    }

    @Override
    @Transactional
    public void deleteStudent(int studentId) {

        studentRepository.deleteById(studentId);
    }
}
