package br.com.mateus.commercemanagementsystem.services;

import br.com.mateus.commercemanagementsystem.dto.CategoryDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.IntegrityViolationException;
import br.com.mateus.commercemanagementsystem.exceptions.ResourceNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.repository.CategoryRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Category createCategory(Category category) {

        Optional<Category> entity = categoryRepository.findByName(category.getName());

        if(entity.isPresent()) {
            throw new EntityAlreadyExistsException("Categoria já existe.");
        }
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Category category) {

        categoryRepository.findById(category.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Categoria não encontrada. ID: " + category.getId()));
        categoryRepository.save(category);
        return category;
    }

    @Transactional
    public void deleteByName(String name) {
    
        Category category = categoryRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Categoria não encontrada. Nome: " + name));
    
        try {
            categoryRepository.delete(category);
            categoryRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IntegrityViolationException("Categoria não pôde ser apagada pois possui produtos associados.");
        }
    }

    @Transactional(readOnly = true)
    public Category findByName(String name) {

        return categoryRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Categoria não encontrada. ID: " + name));
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {

        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(x -> new CategoryDTO(x.getId(), x.getName())).toList();
    }
}
