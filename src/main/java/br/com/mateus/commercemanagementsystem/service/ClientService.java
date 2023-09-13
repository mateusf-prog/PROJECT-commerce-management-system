package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.Client;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.Payment;

import java.time.LocalDate;
import java.util.List;

public interface ClientService {

    Client updateClient(Client client);
    Client createClient(Client client);
    Client readClient(Client client);
    Client deleteClient(Client client);
    Client findByCpf(String cpf);
    Client findByName(String name);

    List<Order> findOrders(Client client);
    List<Payment> findPayments(Client client);

    boolean validatePhoneNumber(String phoneNumber);
    boolean validateCpfNumber(String cpf);
    boolean validateBirthdate(LocalDate birthdate);
}
