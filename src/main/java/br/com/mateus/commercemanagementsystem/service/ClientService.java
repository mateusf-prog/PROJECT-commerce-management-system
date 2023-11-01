package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.Client;
import java.util.List;

public interface ClientService {

    Client updateClient(Client client);
    Client createClient(Client client);
    void deleteByCpf(String cpf);
    Client findByCpf(String cpf);
    List<Client> findByName(String name);

}
