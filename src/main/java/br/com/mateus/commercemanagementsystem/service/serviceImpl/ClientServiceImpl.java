package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityMissingDependencyException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.exceptions.client.ClientNoHasOrdersException;
import br.com.mateus.commercemanagementsystem.exceptions.client.InvalidClientDataException;
import br.com.mateus.commercemanagementsystem.exceptions.client.ClientNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Client;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.repository.ClientRepository;
import br.com.mateus.commercemanagementsystem.service.ClientService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public Client updateClient(Client client) {

        Optional<Client> queryClient = clientRepository.findByCpf(client.getCpf());

         if (queryClient.isEmpty()) {
             throw new EntityNotFoundException("Cliente não encontrado!");
         } else if (isClientDataValid(client)) {
             throw new EntityInvalidDataException("Dados inválidos. Verifique os dados e tente novamente!");
         }

         clientRepository.save(client);
         return client;
    }

    @Override
    @Transactional
    public Client createClient(Client client) {

        Optional<Client> queryClient = clientRepository.findByCpf(client.getCpf());

        if (isClientDataValid(client)) {
            throw new EntityInvalidDataException("Dados inválidos. Verifique os dados e tente novamente!");
        } else if (queryClient.isPresent()) {
            throw new EntityAlreadyExistsException("CPF já cadastrado!");
        }

        clientRepository.save(client);
        return client;
    }

    @Override
    @Transactional
    public void deleteByCpf(String cpf) {

        Optional<Client> client = clientRepository.findByCpf(cpf);

        if (client.isEmpty()) {
            throw new EntityNotFoundException("Cliente não encontrado!");
        }
        clientRepository.delete(client.get());
    }

    @Override
    public Client findByCpf(String cpf) {

        Optional<Client> queryClient = clientRepository.findByCpf(cpf);

        if (queryClient.isEmpty()) {
            throw new EntityNotFoundException("Cliente não encontrado!");
        }
        return queryClient.get();
    }

    @Override
    public List<Client> findByName(String name) {

        List<Client> results = clientRepository.findByName(name);

        if (results.isEmpty()) {
            throw new EntityNotFoundException("Nenhum resultado encontrado!");
        }
        return results;
    }


    @Override
    public List<Order> findOrdersByClientCpf(String cpf) {

        Optional<Client> client = clientRepository.findByCpf(cpf);

        if (client.isEmpty()) {
            throw new EntityNotFoundException("Cliente não encontrado!");
        } else if (client.get().getOrders().isEmpty()) {
            throw new EntityMissingDependencyException("Cliente não possui nenhum pedido!");
        }

        return client.get().getOrders();
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
