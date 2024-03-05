package br.com.mateus.commercemanagementsystem.common;

import java.math.BigDecimal;

import br.com.mateus.commercemanagementsystem.dto.ProductDTO;
import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.model.Product;

public class ProductConstants {
     public static final Product PRODUCT = new Product("iPhone", BigDecimal.valueOf(2500.00), 10, new Category());
     public static final Product INVALID_PRODUCT = new Product();

     public static final ProductDTO PRODUCT_DTO = new ProductDTO(1L, "iPhone", BigDecimal.valueOf(2500.00), 10, "category");
     public static final ProductDTO INVALID_PRODUCT_DTO = new ProductDTO();
}
