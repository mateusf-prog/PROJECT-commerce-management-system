package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Categorie;
import br.com.mateus.commercemanagementsystem.repository.CategorieRepository;
import br.com.mateus.commercemanagementsystem.service.CategorieService;
import jakarta.transaction.Transactional;

@Service
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieServiceImpl(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Override
    @Transactional
    public Categorie createCategorie(Categorie categorie) {
        
          Optional<Categorie> queryCategorie = categorieRepository.findById(categorie.getId());

        if (queryCategorie.isPresent()) {
            throw new EntityAlreadyExistsException("Categoria já existente!");
        }

        categorieRepository.save(categorie);
        return categorie;
    }

    @Override
    @Transactional
    public Categorie updateCategorie(Categorie categorie) {

        Optional<Categorie> queryCategorie = categorieRepository.findById(categorie.getId());

        if (queryCategorie.isEmpty()) {
            throw new EntityNotFoundException("Categoria não encontrada!");
        }
        
        categorieRepository.save(categorie);
        return categorie;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        
        Optional<Categorie> queryCategorie = categorieRepository.findById(id);

        if (queryCategorie.isEmpty()) {
            throw new EntityNotFoundException("Categoria não encontrada!");
        }

        categorieRepository.deleteById(id);
    }

    @Override
    public Categorie findById(Long id) {
        
        Optional<Categorie> queryCategorie = categorieRepository.findById(id);

        if (queryCategorie.isEmpty()) {
            throw new EntityNotFoundException("Categoria não encontrada!");
        }

        return queryCategorie.get();
    }

    @Override
    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }
}
