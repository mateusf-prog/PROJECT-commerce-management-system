package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.Client;
import br.com.mateus.commercemanagementsystem.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public void save(Client client){
        clientRepository.save(client);
    }

    public Object findByCpf(String cpf){
        return clientRepository.findByCpf(cpf);
    }

    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    @Transactional
    public String deleteByCpf(String cpf) {

        if (clientRepository.findByCpf(cpf) == null) {
            return "Client não encontrado!";
        } else {
            clientRepository.deleteByCpf(cpf);
            return "Cliente deletado com sucesso!";
        }
    }
}
