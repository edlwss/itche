package ru.itche.petproject.backendservice.adress.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itche.petproject.backendservice.adress.entity.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
}
