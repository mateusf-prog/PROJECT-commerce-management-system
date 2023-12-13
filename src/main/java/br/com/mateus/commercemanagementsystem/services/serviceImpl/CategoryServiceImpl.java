package br.com.mateus.commercemanagementsystem.services.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.repository.CategoryRepository;
import br.com.mateus.commercemanagementsystem.services.CategoryService;
import jakarta.transaction.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {

        Optional<Category> queryCategory = categoryRepository.findByName(category.getName());
        if (queryCategory.isPresent()) {
            throw new EntityAlreadyExistsException("Categoria já existe!");
        }
        categoryRepository.save(category);
        return category;
    }

    @Override
    @Transactional
    public Category updateCategory(Category category) {

        Optional<Category> queryCategory = categoryRepository.findById(category.getId());
        if (queryCategory.isEmpty()) {
            throw new EntityNotFoundException("Categoria não encontrada!");
        }
        categoryRepository.save(category);
        return queryCategory.get();
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        
        Optional<Category> category = categoryRepository.findByName(name);
        if (category.isEmpty()) {
            throw new EntityNotFoundException("Categoria não encontrada. Nome: " + name);
        }
        categoryRepository.delete(category.get());
    }

    @Override
    public Category findByName(String name) {
        
        Optional<Category> category = categoryRepository.findByName(name);
        if (category.isEmpty()) {
            throw new EntityNotFoundException("Categoria não encontrada!");
        }
        return category.get();
    }

    @Override
    public List<Category> findAll() {

        if (categoryRepository.findAll().isEmpty()) {
            throw new EntityNotFoundException("Nenhuma categoria encontrada!");
        }
        return categoryRepository.findAll();
    }
}
