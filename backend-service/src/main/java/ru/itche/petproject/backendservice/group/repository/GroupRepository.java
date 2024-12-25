package ru.itche.petproject.backendservice.group.repository;

import org.springframework.data.repository.CrudRepository;
import ru.itche.petproject.backendservice.group.entity.Group;

public interface GroupRepository extends CrudRepository<Group, Integer> {
}
