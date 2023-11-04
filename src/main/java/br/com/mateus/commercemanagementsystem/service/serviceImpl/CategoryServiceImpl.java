package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.repository.CategoryRepository;
import br.com.mateus.commercemanagementsystem.service.CategoryService;
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

        List<Category> queryCategory = categoryRepository.findByName(category.getName());

        if (!queryCategory.isEmpty()) {
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
        
        List<Category> categories = categoryRepository.findByName(name);

        if (categories.isEmpty()) {
            throw new EntityNotFoundException("Categoria não encontrada. Nome: " + name);
        }

        if (categories.size() > 1) {
            throw new EntityAlreadyExistsException("Mais de uma categoria encontrada!");
        }

        categoryRepository.delete(categories.get(0));
    }

    @Override
    public Category findByName(String name) {
        
        List<Category> categories = categoryRepository.findByName(name);

        if (categories.isEmpty()) {
            throw new EntityNotFoundException("Categoria não encontrada!");
        }
        
        if (categories.size() > 1) {
            throw new EntityAlreadyExistsException("Mais de uma categoria encontrada!");
        }
        
        return categories.get(0);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
