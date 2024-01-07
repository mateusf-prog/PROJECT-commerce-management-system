package br.com.mateus.commercemanagementsystem.services.serviceImpl;

import br.com.mateus.commercemanagementsystem.dto.CategoryDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.ResourceNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.repository.CategoryRepository;
import br.com.mateus.commercemanagementsystem.repository.ProductRepository;
import br.com.mateus.commercemanagementsystem.services.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {

        Optional<Category> entity = categoryRepository.findByName(category.getName());

        if(entity.isPresent()) {
            throw new EntityAlreadyExistsException("Categoria já existe.");
        }
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category updateCategory(Category category) {

        Category entity = categoryRepository.findById(category.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Categoria não encontrada. ID: " + category.getId()));
        categoryRepository.save(category);
        return category;
    }

    @Override
    @Transactional
    public void deleteByName(String name) {

        Category category = categoryRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Categoria não encontrada. Nome: " + name));

        categoryRepository.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Category findByName(String name) {

        return categoryRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Categoria não encontrada. ID: " + name));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {

        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(x -> new CategoryDTO(x.getId(), x.getName())).toList();
    }
}
