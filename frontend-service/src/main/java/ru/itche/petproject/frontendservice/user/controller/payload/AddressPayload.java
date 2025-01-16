package ru.itche.petproject.frontendservice.user.controller.payload;

public record AddressPayload (String city,
                              String street,
                              String home,
                              String flat){
}
