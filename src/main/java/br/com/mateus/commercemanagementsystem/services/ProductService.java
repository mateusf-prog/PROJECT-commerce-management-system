package br.com.mateus.commercemanagementsystem.services;

import br.com.mateus.commercemanagementsystem.dto.ProductDTO;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Product;

import java.util.List;

public interface ProductService {

    ProductDTO createProduct(Product product);
    ProductDTO findById(Long id);
    ProductDTO updateProduct(Product product);
    ProductDTO findByName(String name);
    List<ProductDTO> findAll();

    void returnQuantityInStockAfterCanceledOrder(List<OrderItem> list);
    int checkQuantityStockAvailability(Long id);
    String adjustStockQuantity(Long id, int newQuantity);
}