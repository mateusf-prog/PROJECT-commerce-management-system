package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.InvalidClientData;
import br.com.mateus.commercemanagementsystem.exceptions.client.ClientNotFound;
import br.com.mateus.commercemanagementsystem.model.Client;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.repository.ClientRepository;
import br.com.mateus.commercemanagementsystem.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client updateClient(Client client) {
         if (isClientDataValid(client)) {
             throw new InvalidClientData("Dados inválidos. Verifique os dados e tente novamente!");
         } else if (clientRepository.findByCpf(client.getCpf()) == null) {
             throw new ClientNotFound("Cliente não encontrado!");
         } else {
             clientRepository.save(client);
             return client;
         }
    }

    @Override
    public Client createClient(Client client) {
        if (isClientDataValid(client)) {
            throw new InvalidClientData("Dados inválidos. Verifique os dados e tente novamente!");
        } else {
            clientRepository.save(client);
            return client;
        }
    }

    @Override
    public Client readClient(Client client) {
        return null;
    }

    @Override
    public String deleteByCpf(String cpf) {
        return null;
    }

    @Override
    public Client findByCpf(String cpf) {
        return null;
    }

    @Override
    public Client findByName(String name) {
        return null;
    }

    @Override
    public List<Order> findOrders(Client client) {
        return null;
    }

    @Override
    public List<Payment> findPayments(Client client) {
        return null;
    }

    @Override
    public String formatBirthdate(LocalDate birthdate) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fmt.format(birthdate);
    }

    // validations methods

    public boolean isClientDataValid(Client client) {
        return validateClientName(client) &&
                validateClientCpfNumber(client) &&
                validateClientPhoneNumber(client) &&
                validateClientBirthdate(client);
    }

    @Override
    public boolean validateClientName(Client client) {
        return client.getName().isBlank();
    }

    @Override
    public boolean validateClientPhoneNumber(Client client) {
        return client.getPhoneNumber().isBlank() || client.getPhoneNumber().length() < 11;
    }

    @Override
    public boolean validateClientCpfNumber(Client client) {
        return client.getCpf().length() < 11 || client.getCpf().isBlank();
    }

    @Override
    public boolean validateClientBirthdate(Client client) {
        return client.getBirthdate().isAfter(LocalDate.now());
    }
}
