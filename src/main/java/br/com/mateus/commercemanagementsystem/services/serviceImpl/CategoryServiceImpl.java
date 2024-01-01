package br.com.mateus.commercemanagementsystem.services.serviceImpl;

import br.com.mateus.commercemanagementsystem.dto.CategoryDTO;
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
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {

        Category checkCategory = checkCategoryExistsByName(category.getName());
        categoryRepository.save(checkCategory);
        return checkCategory;
    }

    @Override
    @Transactional
    public Category updateCategory(Category category) {

        Category checkCategory = checkCategoryExistsById(category.getId());
        categoryRepository.save(category);
        return category;
    }

    @Override
    @Transactional
    public void deleteByName(String name) {

        Category category = checkCategoryExistsByName(name);
        Optional<List<Product>> product = productRepository.findByCategoryName(name);

        if (product.isPresent()) {
            throw new EntityInvalidDataException("Não foi possível apagar a categoria, pois ela está associada a um produto, " +
                    "mude a categoria do produto antes de apagá-la");
        }
        categoryRepository.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Category findByName(String name) {
        
        return checkCategoryExistsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {

        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(x -> new CategoryDTO(x.getId(), x.getName())).toList();

    }

    @Transactional(readOnly = true)
    protected Category checkCategoryExistsById(Long id) {
        Optional<Category> queryCategory = categoryRepository.findById(id);
        if (queryCategory.isEmpty()) {
            throw new ResourceNotFoundException("Categoria não encontrada. ID: " + id);
        }
        return queryCategory.get();
    }

    @Transactional(readOnly = true)
    protected Category checkCategoryExistsByName(String name) {
        Optional<Category> queryCategory = categoryRepository.findByName(name);
        if (queryCategory.isEmpty()) {
            throw new ResourceNotFoundException("Categoria não encontrada. Nome: " + name);
        }
        return queryCategory.get();
    }
}
