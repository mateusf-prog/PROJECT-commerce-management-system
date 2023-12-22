package br.com.mateus.commercemanagementsystem.services;

import br.com.mateus.commercemanagementsystem.dto.ProductDTO;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductDTO createProduct(Product product);
    ProductDTO findById(Long id);
    ProductDTO updateProduct(Product product);
    void deleteProduct(Long id);
    ProductDTO findByName(String name);
    List<ProductDTO> findAll();

    void returnQuantityInStockAfterCanceledOrder(List<OrderItem> list);
    int checkQuantityStockAvailability(String name);
    String adjustStockQuantity(String name, int quantity);
    String setPrice(Long id, BigDecimal price);

}