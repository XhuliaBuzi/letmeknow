package com.letmeknow.service;

import com.letmeknow.model.Category;
import com.letmeknow.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<Category> getCategory(String sort) {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(sort));
        return categoryRepository.findAll(pageable);
    }

    public Optional<Category> getOneCategory(UUID id) {
        return exists(id);
    }

    public Category addNewCategory(Category category) {
        var userByName = categoryRepository.findByName(category.getName());
        if (userByName.isPresent()) throw new IllegalStateException("Category taken.");
        return categoryRepository.save(category);
    }

    public Category updateCategory(UUID categoryID, Category categoryForUpdate) {
        exists(categoryID);
        var byId = categoryRepository.getById(categoryID);
        final var forUpdateName = categoryForUpdate.getName();
        if (areNotEqual(byId.getName(), forUpdateName)) byId.setName(forUpdateName);
        return categoryRepository.save(byId);
    }

    private <T> boolean areNotEqual(T first, T second) {
        return second != null && !Objects.equals(first, second);
    }

    private Optional<Category> exists(UUID id) {
        var byId = categoryRepository.findById(id);
        if (byId.isEmpty()) throw new IllegalStateException("User by Topic : " + id + " does not exists.");
        return byId;
    }
}
