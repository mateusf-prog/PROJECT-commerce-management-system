package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.model.enums.Categories;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductService {

    Product createProduct(Product product);
    Optional<Product> findById(Long id);
    Product updateProduct(Product product);
    String deleteProduct(Product product);
    Product findByName(String name);

    String adjustStockQuantity(Long id, int quantity);
    Product setPrice(Long id, BigDecimal price);

    Product setCategory(Product product, Categories category);

    void checkValidations(Product product);
}
