package br.com.mateus.commercemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateus.commercemanagementsystem.model.Client;

public interface ClientRepository extends JpaRepository<Client, String> {

    Client findByCpf(String cpf);

    void deleteByCpf(String cpf);
}
