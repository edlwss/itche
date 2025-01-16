package ru.itche.petproject.backendservice.adress.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itche.petproject.backendservice.adress.entity.Address;
import ru.itche.petproject.backendservice.adress.payload.NewAddressPayload;
import ru.itche.petproject.backendservice.adress.payload.UpdateAddressPayload;
import ru.itche.petproject.backendservice.adress.repository.AddressRepository;

@Service
@RequiredArgsConstructor
public class DefaultAddressService implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Address createAddress(NewAddressPayload payload) {
        return this.addressRepository.save(new Address(null,
                payload.city(),
                payload.street(),
                payload.home(),
                payload.flat()));
    }

    @Override
    public void updateAddress(Integer addressId, UpdateAddressPayload payload) {
        this.addressRepository.findById(addressId)
                .ifPresent(address -> {
                    address.setCity(payload.city());
                    address.setStreet(payload.street());
                    address.setHome(payload.home());
                    address.setFlat(payload.flat());
                });
    }
}
