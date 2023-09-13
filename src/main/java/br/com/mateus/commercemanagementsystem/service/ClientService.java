package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.Client;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.Payment;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface ClientService {

    Client updateClient(Client client);
    Client createClient(Client client);
    Client readClient(Client client);
    String deleteByCpf(String cpf);
    Client findByCpf(String cpf);
    Client findByName(String name);

    List<Order> findOrders(Client client);
    List<Payment> findPayments(Client client);

    boolean validateClientName(Client client);
    boolean validateClientPhoneNumber(Client client);
    boolean validateClientCpfNumber(Client client);
    boolean validateClientBirthdate(Client client);

    String formatBirthdate(LocalDate birthdate);
}
