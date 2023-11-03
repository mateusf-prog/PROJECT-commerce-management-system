package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);
    Category updateCategory(Category CategoryService);
    void deleteById(Long id);
    Category findById(Long id);

    List<Category> findAll();
}
