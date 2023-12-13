package br.com.mateus.commercemanagementsystem.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.services.serviceImpl.CategoryServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    
    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category) {
        return ResponseEntity.ok().body(categoryService.createCategory(category));
    }

    @GetMapping
    public ResponseEntity<?> listCategories() {
        return ResponseEntity.ok().body(categoryService.findAll());
    }

    @PutMapping
    public ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category) {
        return ResponseEntity.ok().body(categoryService.updateCategory(category));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteCategory(@PathVariable String name) {
        categoryService.deleteByName(name);
        return ResponseEntity.ok().body("Categoria deletada com sucesso \n- Nome: " + name);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Category> findByName(@PathVariable String name) {
        return ResponseEntity.ok().body(categoryService.findByName(name));
    }
}
