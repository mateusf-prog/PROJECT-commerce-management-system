package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.dto.CustomerDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.integration.IntegrationPaymentApiImpl;
import br.com.mateus.commercemanagementsystem.model.Customer;
import br.com.mateus.commercemanagementsystem.repository.CustomerRepository;
import br.com.mateus.commercemanagementsystem.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final IntegrationPaymentApiImpl integrationPaymentApi;

    public CustomerServiceImpl(CustomerRepository customerRepository, IntegrationPaymentApiImpl integrationPaymentApi) {
        this.customerRepository = customerRepository;
        this.integrationPaymentApi = integrationPaymentApi;
    }

    @Override
    public Customer updateCustomer(Customer customer) {

        Optional<Customer> queryCustomer = customerRepository.findByCpf(customer.getCpf());

         if (queryCustomer.isEmpty()) {
             throw new EntityNotFoundException("Cliente não encontrado!");
         }

         customer.setIdApiExternal(queryCustomer.get().getIdApiExternal());

         CustomerDTO customerDTO = integrationPaymentApi.updateCustomer(customer);
         customer.setIdApiExternal(customerDTO.getId());
         customerRepository.save(customer);
         return customer;
    }

    @Override
    public Customer createCustomer(Customer customer) {

        Optional<Customer> queryCustomer = customerRepository.findByCpf(customer.getCpf());

        if (queryCustomer.isPresent()) {
            throw new EntityAlreadyExistsException("CPF já cadastrado no banco de dados");
        }
        // create customer on api and save on local database
        try {
            CustomerDTO customerDTO = integrationPaymentApi.createCustomer(customer);
            customer.setIdApiExternal(customerDTO.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public void deleteByCpf(String cpf) {

        Optional<Customer> customer = customerRepository.findByCpf(cpf);

        if (customer.isEmpty()) {
            throw new EntityNotFoundException("Cliente não encontrado. CPF " + cpf);
        }
        customerRepository.delete(customer.get());
    }

    @Override
    public Customer findByCpf(String cpf) {

        Optional<Customer> queryCustomer = customerRepository.findByCpf(cpf);
        if (queryCustomer.isEmpty()) {
            throw new EntityNotFoundException("Cliente não encontrado!");
        }
        return queryCustomer.get();
    }

    @Override
    public List<Customer> findByName(String name) {

        List<Customer> results = customerRepository.findByName(name);
        if (results.isEmpty()) {
            throw new EntityNotFoundException("Nenhum resultado encontrado!");
        }
        return results;
    }
}




