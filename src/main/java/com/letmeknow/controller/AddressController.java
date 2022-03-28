package com.letmeknow.controller;

import com.letmeknow.model.Address;
import com.letmeknow.service.AddressService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public Page<Address> getAddress(@RequestParam(required = false, value = "sort", defaultValue = "email") String sort) {
        return addressService.getAddress(sort);
    }

    @GetMapping(path = "/{id}")
    public Optional<Address> getOneAddress(@PathVariable("id") UUID id) {
        return addressService.getOneAddress(id);
    }

    @PostMapping
    public Address registerNewAddress(@RequestBody Address address) {
        return addressService.addNewAddress(address);
    }

    @PatchMapping(path = "/{addressID}")
    public Address updateAddress(
            @PathVariable("addressID") UUID uuid,
            @RequestBody Address address) {
        return addressService.updateAddress(uuid, address);
    }


    @DeleteMapping(path = "/{id}")
    public void deleteUser(@PathVariable("id") UUID id) {
        addressService.deleteAddress(id);
    }
}
