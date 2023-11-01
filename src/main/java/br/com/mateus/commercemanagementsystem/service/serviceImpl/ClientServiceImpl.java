package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Client;
import br.com.mateus.commercemanagementsystem.repository.ClientRepository;
import br.com.mateus.commercemanagementsystem.service.ClientService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
         }

         clientRepository.save(client);
         return client;
    }

    @Override
    @Transactional
    public Client createClient(Client client) {

        Optional<Client> queryClient = clientRepository.findByCpf(client.getCpf());

        if (queryClient.isPresent()) {
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
            throw new EntityNotFoundException("Cliente não encontrado. CPF " + cpf);
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
}




