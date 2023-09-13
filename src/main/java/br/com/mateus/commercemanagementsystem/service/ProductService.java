package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.model.enums.Categories;

import java.math.BigDecimal;

public interface ProductService {

    Product createProduct(Product product);
    Product readProduct(Product product);
    Product updateProduct(Product product);
    String deleteProduct(Product product);
    Product findByName(String name);
    Product findById(Long id);

    String adjustStockQuantity(Long id, int quantity);
    Product setPrice(Long id, BigDecimal price);

    Product setCategory(Product product, Categories category);

    boolean validateProductName(Product product);
    boolean validateProductQuantity(Product product);
    boolean validateProductPrice(Product product);
    boolean validateProductCategory(Product product);
}
