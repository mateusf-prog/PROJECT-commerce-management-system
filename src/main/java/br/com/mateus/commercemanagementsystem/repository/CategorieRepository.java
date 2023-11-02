package br.com.mateus.commercemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateus.commercemanagementsystem.model.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    
}
