package br.com.mateus.commercemanagementsystem.services;

import br.com.mateus.commercemanagementsystem.dto.CustomerCreatedOrUpdatedDTO;
import br.com.mateus.commercemanagementsystem.dto.CustomerDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.ResourceNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Customer;
import br.com.mateus.commercemanagementsystem.repository.CustomerRepository;
import br.com.mateus.commercemanagementsystem.services.asaas_integration.CustomerApiService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerApiService integrationPaymentApi;

    public CustomerService(CustomerRepository customerRepository, CustomerApiService integrationPaymentApi) {
        this.customerRepository = customerRepository;
        this.integrationPaymentApi = integrationPaymentApi;
    }

    @Transactional
    public CustomerCreatedOrUpdatedDTO createCustomer(Customer customer) {

        Optional<Customer> queryCustomer = customerRepository.findByCpf(customer.getCpf());
        if (queryCustomer.isPresent()) {
            throw new EntityAlreadyExistsException("Cliente já cadastrado.");
        }
        CustomerDTO customerWithIdApiExternal = integrationPaymentApi.createCustomer(customer);

        customer.setIdApiExternal(customerWithIdApiExternal.getId());
        customerRepository.save(customer);
        return new CustomerCreatedOrUpdatedDTO(customer);
    }

    @Transactional
    public CustomerCreatedOrUpdatedDTO updateCustomer(Customer customer) {

        Customer queryCustomer = customerRepository.findByCpf(customer.getCpf()).orElseThrow(
                () -> new ResourceNotFoundException("Cliente não encontrado"));

        
        customer.setIdApiExternal(queryCustomer.getIdApiExternal());
        integrationPaymentApi.updateCustomer(customer);
        customerRepository.save(customer);
        return new CustomerCreatedOrUpdatedDTO(queryCustomer);
    }
    @Transactional(readOnly = true)
    public Customer findByCpf(String cpf) {

        return customerRepository.findByCpf(cpf).orElseThrow(
                () -> new ResourceNotFoundException("Cliente não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<CustomerCreatedOrUpdatedDTO> findAll() {

        List<Customer> list = customerRepository.findAll();
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Lista vazia.");
        }

        return list.stream().map(CustomerCreatedOrUpdatedDTO::new).toList();
    }
}




