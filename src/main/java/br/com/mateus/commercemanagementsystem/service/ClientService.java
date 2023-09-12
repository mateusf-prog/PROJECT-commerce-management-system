package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.Client;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.repository.ClientRepository;
import br.com.mateus.commercemanagementsystem.repository.PaymentRepository;
import jakarta.persistence.PreRemove;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PaymentService paymentService;

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
            return "Cliente não encontrado!";
        } else {
            Client client = clientRepository.findByCpf(cpf);
            paymentService.setNullEntityPaymentsBeforeRemoveClient(client);
            clientRepository.deleteByCpf(cpf);
            return "Cliente deletado com sucesso!";
        }
    }
}
