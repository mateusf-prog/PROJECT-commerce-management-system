package br.com.mateus.commercemanagementsystem.services;

import br.com.mateus.commercemanagementsystem.dto.CategoryDTO;
import br.com.mateus.commercemanagementsystem.model.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);
    Category updateCategory(Category category);
    void deleteByName(String name);
    Category findByName(String name);

    List<CategoryDTO> findAll();
}
