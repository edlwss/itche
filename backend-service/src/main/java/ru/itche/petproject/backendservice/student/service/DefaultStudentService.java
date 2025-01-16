package ru.itche.petproject.backendservice.student.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itche.petproject.backendservice.group.entity.Group;
import ru.itche.petproject.backendservice.group.repository.GroupRepository;
import ru.itche.petproject.backendservice.student.controller.payload.NewStudentPayload;
import ru.itche.petproject.backendservice.student.controller.payload.UpdateStudentPayload;
import ru.itche.petproject.backendservice.student.entity.Student;
import ru.itche.petproject.backendservice.student.repository.StudentRepository;
import ru.itche.petproject.backendservice.user.entity.Role;
import ru.itche.petproject.backendservice.user.entity.User;
import ru.itche.petproject.backendservice.user.repository.RoleRepository;
import ru.itche.petproject.backendservice.user.repository.UserRepository;
import ru.itche.petproject.backendservice.user.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultStudentService implements StudentService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;

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
    public Student createStudent(NewStudentPayload payload, Integer groupId) {
        Role studentRole = roleRepository.findByNameRole("ROLE_STUDENT")
                .orElseThrow(() -> new IllegalStateException("Role STUDENT not found"));

        User user = userService.createUser(
                payload.userPayload().lastName(),
                payload.userPayload().firstName(),
                payload.userPayload().middleName(),
                payload.userPayload().dateOfBirth(),
                payload.userPayload().photo(),
                payload.userPayload().phoneNumber(),
                payload.userPayload().email(),
                payload.userPayload().username(),
                payload.userPayload().password(),
                studentRole,
                payload.userPayload().cardPayload().passportSeries(),
                payload.userPayload().cardPayload().passportNumber(),
                payload.userPayload().cardPayload().issuedBy(),
                payload.userPayload().cardPayload().birthCertificateNumber(),
                payload.userPayload().cardPayload().issueDate(),
                payload.userPayload().addressPayload().city(),
                payload.userPayload().addressPayload().street(),
                payload.userPayload().addressPayload().home(),
                payload.userPayload().addressPayload().flat()
        );

        Group group = groupRepository.findById(groupId).orElse(null);

        return studentRepository.save(new Student(null, user, group, payload.details()));
    }

    @Override
    @Transactional
    public void updateStudent(Integer studentId, UpdateStudentPayload payload) {

        this.studentRepository.findById(studentId)
                .ifPresent(student -> {
                    Integer userId = student.getUser().getId();

                    this.userService.updateUser(userId,
                            payload.userPayload().lastName(),
                            payload.userPayload().firstName(),
                            payload.userPayload().middleName(),
                            payload.userPayload().dateOfBirth(),
                            payload.userPayload().photo(),
                            payload.userPayload().phoneNumber(),
                            payload.userPayload().email(),
                            payload.userPayload().cardPayload().passportSeries(),
                            payload.userPayload().cardPayload().passportNumber(),
                            payload.userPayload().cardPayload().issuedBy(),
                            payload.userPayload().cardPayload().birthCertificateNumber(),
                            payload.userPayload().cardPayload().issueDate(),
                            payload.userPayload().addressPayload().city(),
                            payload.userPayload().addressPayload().street(),
                            payload.userPayload().addressPayload().home(),
                            payload.userPayload().addressPayload().flat());


                    student.setDetails(payload.details());
                    student.setGroup(groupRepository.findById(payload.group()).orElse(null));
                });
    }

    @Override
    @Transactional
    public void deleteStudent(int studentId) {
        studentRepository.deleteById(studentId);
    }
}
