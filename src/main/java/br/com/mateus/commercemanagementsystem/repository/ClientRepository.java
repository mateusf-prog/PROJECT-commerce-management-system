package br.com.mateus.commercemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateus.commercemanagementsystem.model.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {

    Optional<Client> findByName(String name);

    Client findByCpf(String cpf);
}
