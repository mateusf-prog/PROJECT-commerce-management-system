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
    public CategoryDTO createCategory(CategoryDTO dto) {

        Optional<Category> entity = categoryRepository.findByName(dto.getName());

        if(entity.isPresent()) {
            throw new EntityAlreadyExistsException("Categoria já cadastrada. Nome: " + dto.getName());
        }

        Category category = new Category();
        category.setName(dto.getName());
        categoryRepository.saveAndFlush(category);
        dto.setId(category.getId());
        return dto;
    }

    @Transactional
    public Category updateCategory(Category category) {

        if (category.getId() == null) {
            throw new ResourceNotFoundException("Categoria deve conter um ID válido.");
        }

        categoryRepository.findById(category.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Categoria não encontrada. ID: " + category.getId()));
        categoryRepository.save(category);
        return category;
    }

    @Transactional
    public void deleteByName(String name) {
    
        Optional<Category> category = categoryRepository.findByName(name);
        if(category.isEmpty()) {
            throw new ResourceNotFoundException("Categoria não encontrada. Nome: " + name);
        }
    
        try {
            categoryRepository.delete(category.get());
            categoryRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IntegrityViolationException("Categoria não pôde ser apagada pois possui produtos associados.");
        }
    }

    @Transactional(readOnly = true)
    public Category findByName(String name) {

        Optional<Category> category = categoryRepository.findByName(name);
        if(category.isEmpty()) {
            throw new ResourceNotFoundException("Categoria não encontrada. Nome: " + name);
        }

        return category.get();
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {

        List<Category> categoryList = categoryRepository.findAll();

        if (categoryList.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma categoria encontrada.");
        }
        return categoryList.stream().map(x -> new CategoryDTO(x.getId(), x.getName())).toList();
    }
}
