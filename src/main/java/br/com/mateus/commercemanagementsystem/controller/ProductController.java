package br.com.mateus.commercemanagementsystem.controller;

import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.repository.ProductRepository;
import br.com.mateus.commercemanagementsystem.service.serviceImpl.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        productService.createProduct(product);
        return ResponseEntity.ok().body(product);
    }
}
