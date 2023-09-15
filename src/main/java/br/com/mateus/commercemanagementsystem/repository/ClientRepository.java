package br.com.mateus.commercemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateus.commercemanagementsystem.model.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, String> {

    List<Client> findByName(String name);

    Client findByCpf(String cpf);
}
