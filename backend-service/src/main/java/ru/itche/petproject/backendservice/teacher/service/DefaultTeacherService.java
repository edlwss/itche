package ru.itche.petproject.backendservice.teacher.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itche.petproject.backendservice.teacher.controller.payload.NewTeacherPayload;
import ru.itche.petproject.backendservice.teacher.controller.payload.UpdateTeacherPayload;
import ru.itche.petproject.backendservice.teacher.entity.Teacher;
import ru.itche.petproject.backendservice.teacher.repository.TeacherRepository;
import ru.itche.petproject.backendservice.user.entity.Role;
import ru.itche.petproject.backendservice.user.entity.User;
import ru.itche.petproject.backendservice.user.repository.RoleRepository;
import ru.itche.petproject.backendservice.user.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultTeacherService implements TeacherService {

    private final TeacherRepository teacherRepository;
    private  final RoleRepository roleRepository;
    private final UserService userService;

    @Override
    public Iterable<Teacher> getAllTeachers() {
        return this.teacherRepository.findAll();
    }

    @Override
    @Transactional
    public Teacher createTeacher(NewTeacherPayload payload) {

        Role studentRole = roleRepository.findByNameRole("ROLE_TEACHER")
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
                studentRole
        );

        return teacherRepository.save(new Teacher(null, user, payload.education(), payload.details()));
    }

    @Override
    public Optional<Teacher> findTeacher(int teacherId) {
        return this.teacherRepository.findById(teacherId);
    }

    @Override
    @Transactional
    public void updateTeacher(int teacherId, UpdateTeacherPayload payload) {
        this.teacherRepository.findById(teacherId)
                .ifPresent(teacher -> {
                    Integer userId = teacher.getUser().getId();

                    this.userService.updateUser(userId,
                            payload.userPayload().lastName(),
                            payload.userPayload().firstName(),
                            payload.userPayload().middleName(),
                            payload.userPayload().dateOfBirth(),
                            payload.userPayload().photo(),
                            payload.userPayload().phoneNumber(),
                            payload.userPayload().email());

                    teacher.setEducation(payload.education());
                    teacher.setDetails(payload.details());
                });
    }

    @Override
    @Transactional
    public void deleteTeacher(int teacherId) {
        this.teacherRepository.deleteById(teacherId);
    }
}
