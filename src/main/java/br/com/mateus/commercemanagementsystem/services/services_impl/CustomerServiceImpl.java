package br.com.mateus.commercemanagementsystem.services.services_impl;

import br.com.mateus.commercemanagementsystem.dto.CustomerCreatedOrUpdatedDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.ResourceNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Customer;
import br.com.mateus.commercemanagementsystem.repository.CustomerRepository;
import br.com.mateus.commercemanagementsystem.services.CustomerService;
import br.com.mateus.commercemanagementsystem.services.services_asaas_integration.impl.CustomerApiServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerApiServiceImpl integrationPaymentApi;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerApiServiceImpl integrationPaymentApi) {
        this.customerRepository = customerRepository;
        this.integrationPaymentApi = integrationPaymentApi;
    }

    @Override
    @Transactional
    public CustomerCreatedOrUpdatedDTO createCustomer(Customer customer) {

        Optional<Customer> queryCustomer = customerRepository.findByCpf(customer.getCpf());
        if (queryCustomer.isPresent()) {
            throw new EntityAlreadyExistsException("Cliente já cadastrado.");
        }

        customerRepository.save(customer);
        return new CustomerCreatedOrUpdatedDTO(customer);
    }

    @Override
    @Transactional
    public CustomerCreatedOrUpdatedDTO updateCustomer(Customer customer) {

        Customer queryCustomer = customerRepository.findByCpf(customer.getCpf()).orElseThrow(
                () -> new ResourceNotFoundException("Cliente não encontrado"));

        if (queryCustomer.getIdApiExternal() != null) {
            integrationPaymentApi.updateCustomer(customer);
        }

        customerRepository.save(customer);
        return new CustomerCreatedOrUpdatedDTO(queryCustomer);
    }

    @Override
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




