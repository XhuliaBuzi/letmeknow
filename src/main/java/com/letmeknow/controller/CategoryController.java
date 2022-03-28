package com.letmeknow.controller;

import com.letmeknow.model.Category;
import com.letmeknow.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Page<Category> getCategory(@RequestParam(required = false, value = "sort", defaultValue = "name") String sort) {
        return categoryService.getCategory(sort);
    }

    @GetMapping(path = "/{id}")
    public Optional<Category> getOneCategory(@PathVariable("id") UUID id) {
        return categoryService.getOneCategory(id);
    }

    @PostMapping
    public Category registerNewTopic(@RequestBody Category category) {
        return categoryService.addNewCategory(category);
    }

    @PatchMapping(path = "/{categoryID}")
    public Category updateTopic(
            @PathVariable("categoryID") UUID categoryID,
            @RequestBody Category category) {
        return categoryService.updateCategory(categoryID, category);
    }
}
