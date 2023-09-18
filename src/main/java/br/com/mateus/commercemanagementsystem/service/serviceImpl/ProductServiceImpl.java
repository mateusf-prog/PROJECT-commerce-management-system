package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.product.*;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.model.enums.Categories;
import br.com.mateus.commercemanagementsystem.repository.ProductRepository;
import br.com.mateus.commercemanagementsystem.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public Product createProduct(Product product) {

        try {
            checkValidations(product);
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
            checkValidations(product);
            productRepository.save(product);
            return product;
        }
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
    public String adjustStockQuantity(Long id, int quantity) {
        return null;
    }

    @Override
    public Product setPrice(Long id, BigDecimal price) {
        return null;
    }

    @Override
    public Product setCategory(Product product, Categories category) {
        return null;
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
