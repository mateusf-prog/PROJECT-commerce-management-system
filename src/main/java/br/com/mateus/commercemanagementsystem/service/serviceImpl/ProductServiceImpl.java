package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.product.*;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.model.enums.Categories;
import br.com.mateus.commercemanagementsystem.repository.ProductRepository;
import br.com.mateus.commercemanagementsystem.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public Product createProduct(Product product) {

        try {
            checkValidations(product);
            validateCategory(product.getCategory().toString());
            productRepository.save(product);
        } catch (Exception e) {
            throw e;
        }

        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()) {
            throw new ProductNotFoundException("Produto não encontrado!");
        } else {
            return product;
        }
    }

    @Override
    @Transactional
    public Product updateProduct(Product product) {

        Optional<Product> productQuery = productRepository.findById(product.getId());

        if (productQuery.isEmpty()) {
            throw new ProductNotFoundException("Produto não encontrado!");
        } else {
            try {
                checkValidations(product);
                validateCategory(product.getCategory().toString());
                productRepository.save(product);
            } catch (Exception e) {
                throw e;
            }
        }

        return product;
    }

    @Override
    @Transactional
    public String deleteProduct(Long id) {

        Optional<Product> productQuery = productRepository.findById(id);

        if (productQuery.isEmpty()) {
            throw new ProductNotFoundException("Produto não encontrado!");
        } else {
            productRepository.deleteById(id);
            return "Produto deletado com sucesso!";
        }
    }

    @Override
    public List<Product> findByName(String name) {

        List<Product> products = productRepository.findByName(name);

        if (products.isEmpty()) {
            throw new ProductNotFoundException("Produto não encontrado!");
        } else {
            return products;
        }
    }

    @Override
    public List<Product> findAll() {

        return productRepository.findAll();
    }

    @Override
    @Transactional
    public String adjustStockQuantity(Long id, int quantity) {

        Optional<Product> productQuery = productRepository.findById(id);

        if (productQuery.isEmpty()) {
            throw new ProductNotFoundException("Produto não encontrado!");
        } else if (quantity < 0) {
            throw new InvalidQuantityStockProductException("Quantidade inválida!");
        } else {
            productQuery.get().setQuantity(quantity);
            updateProduct(productQuery.get());
            return "Quantidade em estoque atualizada!";
        }
    }

    @Override
    @Transactional
    public String setPrice(Long id, BigDecimal price) {

        Optional<Product> productQuery = productRepository.findById(id);

        if (productQuery.isEmpty()) {
            throw new ProductNotFoundException("Produto não encontrado!");
        } else if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPriceProductException("Preço inválido!");
        } else {
            productQuery.get().setPrice(price);
            updateProduct(productQuery.get());
            return "Preço atualizado!";
        }
    }

    @Override
    public Categories parseCategory(String categoryString) {
        try {
            return Categories.valueOf(categoryString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCategoryProductException("Categoria inexistente!");
        }
    }

    @Override
    public void validateCategory(String category) {

        Categories parsedCategory = parseCategory(category);

        if (parsedCategory == null) {
            throw new InvalidCategoryProductException("Categoria inválida!");
        }
    }

    @Override
    public void checkValidations(Product product) {

        if (product.getName().isBlank() || product.getName().length() < 4) {
            throw new InvalidNameProductException("Nome inválido!");
        }
        if (product.getCategory() == null) {
            throw new InvalidCategoryProductException("A categoria do produto não pode ser vazia!");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPriceProductException("O preço do produto deve ser válido!");
        }
        if (product.getQuantity() < 0) {
            throw new InvalidQuantityStockProductException("A quantidade em estoque deve ser válida!");
        }
    }
}