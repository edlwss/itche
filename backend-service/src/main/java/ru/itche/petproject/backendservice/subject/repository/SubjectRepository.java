package ru.itche.petproject.backendservice.subject.repository;

import org.springframework.data.repository.CrudRepository;
import ru.itche.petproject.backendservice.subject.entity.Subject;

public interface SubjectRepository extends CrudRepository<Subject, Integer> {
}
