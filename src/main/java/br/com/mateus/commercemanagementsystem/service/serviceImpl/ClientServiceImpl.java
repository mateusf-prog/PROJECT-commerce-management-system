package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.client.ClientAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.client.InvalidClientDataException;
import br.com.mateus.commercemanagementsystem.exceptions.client.ClientNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Client;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.repository.ClientRepository;
import br.com.mateus.commercemanagementsystem.service.ClientService;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Transactional
    public Client updateClient(Client client) {

         if (isClientDataValid(client)) {
             throw new InvalidClientDataException("Dados inválidos. Verifique os dados e tente novamente!");
         } else if (clientRepository.findByCpf(client.getCpf()) == null) {
             throw new ClientNotFoundException("Cliente não encontrado!");
         } else {
             clientRepository.save(client);
             return client;
         }
    }


    @Override
    @Transactional
    public Client createClient(Client client) {

        Client queryClient = clientRepository.findByCpf(client.getCpf());

        if (isClientDataValid(client)) {
            throw new InvalidClientDataException("Dados inválidos. Verifique os dados e tente novamente!");
        } else if (queryClient != null) {
            throw new ClientAlreadyExistsException("CPF já cadastrado!");
        }

        clientRepository.save(client);
        return client;
    }


    @Override
    @Transactional
    public String deleteByCpf(String cpf) {

        Client client = clientRepository.findByCpf(cpf);

        if (client == null) {
            throw new ClientNotFoundException("Cliente não encontrado!");
        } else {
            clientRepository.delete(client);
            return "Cliente deletado com sucesso!";
        }
    }

    @Override
    public Client findByCpf(String cpf) {

        Client client = clientRepository.findByCpf(cpf);

        if (client == null) {
            throw new ClientNotFoundException("Cliente não encontrado!");
        } else {
            return client;
        }
    }

    @Override
    @Transactional
    public Optional<Client> findByName(String name) {

        Optional<Client> client = clientRepository.findByName(name);

        if (client.isEmpty()) {
            throw new ClientNotFoundException("Cliente não encontrado!");
        } else {
            return client;
        }
    }

    @Override
    public List<Order> findOrders(String cpf) {

        Client client = clientRepository.findByCpf(cpf);

        if (client == null) {
            throw new ClientNotFoundException("Cliente não encontrado!");
        } else {
            return client.getOrders();
        }
    }

    @Override
    public List<Payment> findPayments(String cpf) {

        Client client = clientRepository.findByCpf(cpf);

        if (client == null) {
            throw new ClientNotFoundException("Cliente não encontrado!");
        } else {
            return client.getPayments();
        }
    }

    @Override
    public String formatBirthdate(LocalDate birthdate) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fmt.format(birthdate);
    }

    // validations methods

    public boolean isClientDataValid(Client client) {
        return validateClientName(client) ||
                validateClientCpfNumber(client) ||
                validateClientPhoneNumber(client) ||
                validateClientBirthdate(client);
    }

    @Override
    public boolean validateClientName(Client client) {
        return client.getName().length() < 3;
    }

    @Override
    public boolean validateClientPhoneNumber(Client client) {
        return client.getPhoneNumber().length() != 11;
    }

    @Override
    public boolean validateClientCpfNumber(Client client) {
        return client.getCpf().length() != 11;
    }

    @Override
    public boolean validateClientBirthdate(Client client) {
        LocalDate today = LocalDate.now();
        LocalDate nineYearsBefore = today.minusYears(9);

        return client.getBirthdate().isAfter(LocalDate.now()) ||
                client.getBirthdate().isAfter(nineYearsBefore);
    }
}
