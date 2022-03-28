package com.letmeknow.service;

import com.letmeknow.model.User;
import com.letmeknow.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Page<User> getUsers(String sort) {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(sort));
        return userRepository.findAll(pageable);
    }

    public Optional<User> getOneUser(UUID id) {
        return exists(id);
    }

    public User addNewUser(User user) {
        var userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByEmail.isPresent()) throw new IllegalStateException("Email taken.");
        return userRepository.save(user);
    }

    public User updateUser(UUID userID, User userForUpdate) {
        exists(userID);
        var userById = userRepository.getById(userID);
        final var forUpdateEmail = userForUpdate.getEmail();
        if (areNotEqual(userById.getEmail(), forUpdateEmail)) userById.setEmail(forUpdateEmail);
        return userRepository.save(userById);
    }

    private <T> boolean areNotEqual(T first, T second) {
        return second != null && !Objects.equals(first, second);
    }

    private Optional<User> exists(UUID id) {
        var userByID = userRepository.findById(id);
        if (userByID.isEmpty()) throw new IllegalStateException("User by Email : " + id + " does not exists.");
        return userByID;
    }
}
