package br.com.mateus.commercemanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateus.commercemanagementsystem.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByName(String name);
    
}
