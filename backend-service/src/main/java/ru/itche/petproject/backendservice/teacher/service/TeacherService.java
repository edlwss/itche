package ru.itche.petproject.backendservice.teacher.service;

import ru.itche.petproject.backendservice.teacher.controller.payload.NewTeacherPayload;
import ru.itche.petproject.backendservice.teacher.controller.payload.UpdateTeacherPayload;
import ru.itche.petproject.backendservice.teacher.entity.Teacher;

import java.util.Optional;

public interface TeacherService {
    
    Iterable<Teacher> getAllTeachers();

    Teacher createTeacher(NewTeacherPayload payload);

    Optional<Teacher> findTeacher(int teacherId);

    void updateTeacher(int teacherId, UpdateTeacherPayload payload);

    void deleteTeacher(int teacherId);
}
