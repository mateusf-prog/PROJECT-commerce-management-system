package br.com.mateus.commercemanagementsystem.controllers;

import br.com.mateus.commercemanagementsystem.dto.ProductDTO;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.services.serviceImpl.ProductServiceImpl;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody Product product) {
        ProductDTO productDTO = productService.createProduct(product);
        return ResponseEntity.ok().body(productDTO);
    }

    @PutMapping()
    public ResponseEntity<ProductDTO> update(@Valid @RequestBody Product product) {
        ProductDTO dto = productService.updateProduct(product);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO product = productService.findById(id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDTO> findByName(@PathVariable String name) {
        ProductDTO product = productService.findByName(name);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<ProductDTO> products = productService.findAll();
        return ResponseEntity.ok().body(products);
    }
}