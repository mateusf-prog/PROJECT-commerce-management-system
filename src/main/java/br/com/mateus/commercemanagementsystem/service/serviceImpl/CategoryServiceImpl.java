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

        categoryRepository.save(category);
        return category;
    }

    @Override
    @Transactional
    public Category updateCategory(Category category) {

        Optional<Category> queryCategorie = categoryRepository.findById(category.getId());

        if (queryCategorie.isEmpty()) {
            throw new EntityNotFoundException("Categoria não encontrada!");
        }
        
        categoryRepository.save(category);
        return category;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        
        Optional<Category> queryCategorie = categoryRepository.findById(id);

        if (queryCategorie.isEmpty()) {
            throw new EntityNotFoundException("Categoria não encontrada!");
        }

        categoryRepository.deleteById(id);
    }

    @Override
    public Category findById(Long id) {
        
        Optional<Category> queryCategorie = categoryRepository.findById(id);

        if (queryCategorie.isEmpty()) {
            throw new EntityNotFoundException("Categoria não encontrada!");
        }

        return queryCategorie.get();
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
