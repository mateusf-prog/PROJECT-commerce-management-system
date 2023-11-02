package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.Categorie;

import java.util.List;

public interface CategorieService {

    Categorie createCategorie(Categorie categorie);
    Categorie updateCategorie(Categorie categorie);
    void deleteById(Long id);
    Categorie findById(Long id);

    List<Categorie> findAll();
}
