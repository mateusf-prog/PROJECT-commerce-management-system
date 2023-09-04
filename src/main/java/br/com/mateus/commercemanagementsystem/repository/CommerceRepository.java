package br.com.mateus.commercemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateus.commercemanagementsystem.model.Commerce;

public interface CommerceRepository extends JpaRepository<Commerce, Integer>{
    
}
