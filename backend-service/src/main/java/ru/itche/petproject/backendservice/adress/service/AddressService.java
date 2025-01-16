package ru.itche.petproject.backendservice.adress.service;

import ru.itche.petproject.backendservice.adress.entity.Address;
import ru.itche.petproject.backendservice.adress.payload.NewAddressPayload;
import ru.itche.petproject.backendservice.adress.payload.UpdateAddressPayload;

public interface AddressService {

    Address createAddress(NewAddressPayload payload);

    void updateAddress(Integer addressId,UpdateAddressPayload payload);
}
