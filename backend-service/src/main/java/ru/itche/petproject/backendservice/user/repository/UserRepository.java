package ru.itche.petproject.backendservice.user.repository;

import org.springframework.data.repository.CrudRepository;
import ru.itche.petproject.backendservice.user.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
