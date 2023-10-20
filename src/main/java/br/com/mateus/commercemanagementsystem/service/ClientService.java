package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.model.Client;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.Payment;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClientService {

    Client updateClient(Client client);
    Client createClient(Client client);
    void deleteByCpf(String cpf);
    Client findByCpf(String cpf);
    List<Client> findByName(String name);

    boolean validateClientName(Client client);
    boolean validateClientPhoneNumber(Client client);
    boolean validateClientCpfNumber(Client client);
    boolean validateClientBirthdate(Client client);
}
