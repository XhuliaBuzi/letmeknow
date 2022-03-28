package com.letmeknow.controller;

import com.letmeknow.model.User;
import com.letmeknow.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<User> getUsers(@RequestParam(required = false, value = "sort", defaultValue = "email") String sort) {
        return userService.getUsers(sort);
    }

    @GetMapping(path = "/{id}")
    public Optional<User> getOneUser(@PathVariable("id") UUID id) {
        return userService.getOneUser(id);
    }

    @PostMapping
    public User registerNewUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }

    @PatchMapping(path = "/{userID}")
    public User updateUser(
            @PathVariable("userID") UUID userID,
            @RequestBody User user) {
        return userService.updateUser(userID, user);
    }
}
