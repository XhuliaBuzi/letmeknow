package com.letmeknow.service;

import com.letmeknow.model.Address;
import com.letmeknow.repository.AddressRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Page<Address> getAddress(String sort) {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(sort));
        return addressRepository.findAll(pageable);
    }

    public Optional<Address> getOneAddress(UUID id) {
        return exists(id);
    }

    public Address addNewAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddress(UUID id) {
        exists(id);
        addressRepository.deleteById(id);

    }

    public Address updateAddress(UUID userID, Address addressForUpdate) {
        exists(userID);
        var userById = addressRepository.getById(userID);
        final var forUpdateFloor = addressForUpdate.getFloor();
        final var forUpdateCity = addressForUpdate.getCity();
        final var forUpdateStreet = addressForUpdate.getStreet();
        final var forUpdateCountry = addressForUpdate.getCountry();
        if (areNotEqual(userById.getFloor(), forUpdateFloor)) userById.setFloor(forUpdateFloor);
        if (areNotEqual(userById.getCity(), forUpdateCity)) userById.setCity(forUpdateCity);
        if (areNotEqual(userById.getStreet(), forUpdateStreet)) userById.setStreet(forUpdateStreet);
        if (areNotEqual(userById.getCountry(), forUpdateCountry)) userById.setCountry(forUpdateCountry);
        return addressRepository.save(userById);
    }

    private <T> boolean areNotEqual(T first, T second) {
        return second != null && !Objects.equals(first, second);
    }

    private Optional<Address> exists(UUID id) {
        var userByID = addressRepository.findById(id);
        if (userByID.isEmpty()) throw new IllegalStateException("User by Email : " + id + " does not exists.");
        return userByID;
    }
}
