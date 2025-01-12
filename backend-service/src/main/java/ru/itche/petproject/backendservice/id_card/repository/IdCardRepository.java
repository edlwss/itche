package ru.itche.petproject.backendservice.id_card.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itche.petproject.backendservice.id_card.entity.IdCard;

@Repository
public interface IdCardRepository extends CrudRepository<IdCard, Integer> {
}
