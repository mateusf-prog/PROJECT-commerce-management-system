package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product createProduct(Product product);
    Optional<Product> findById(Long id);
    Product updateProduct(Product product);
    void deleteProduct(Long id);
    Optional<Product> findByName(String name);
    List<Product> findAll();

    int checkQuantityStockAvailability(String name);
    String adjustStockQuantity(String name, int quantity);
    String setPrice(Long id, BigDecimal price);

}