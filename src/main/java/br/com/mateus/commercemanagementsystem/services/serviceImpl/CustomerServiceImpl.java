package br.com.mateus.commercemanagementsystem.services.serviceImpl;

import br.com.mateus.commercemanagementsystem.dto.CustomerCreatedDTO;
import br.com.mateus.commercemanagementsystem.dto.CustomerDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.integration.CustomerApiServiceImpl;
import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.model.Customer;
import br.com.mateus.commercemanagementsystem.repository.CustomerRepository;
import br.com.mateus.commercemanagementsystem.services.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Transactional
    public Customer createCustomer(Customer customer) {

        Optional<Customer> queryCustomer = customerRepository.findByCpf(customer.getCpf());
        Optional<Customer> queryCustomerByEmail = customerRepository.findByEmail(customer.getEmail());

        if (queryCustomer.isPresent() || queryCustomerByEmail.isPresent()) {
            throw new EntityAlreadyExistsException("Cliente já cadastrado.");
        }

        CustomerDTO customerDTO = integrationPaymentApi.createCustomer(customer);
        customer.setIdApiExternal(customerDTO.getId());
        customerRepository.save(customer);
        return customer;
    }

    @Override
    @Transactional
    public void deleteByCpf(String cpf) {

        Optional<Customer> customer = customerRepository.findByCpf(cpf);

        if (customer.isEmpty()) {
            throw new EntityNotFoundException("Cliente não encontrado. CPF " + cpf);
        }
        customerRepository.delete(customer.get());
        if (customer.get().getIdApiExternal() == null) {
            throw new EntityNotFoundException("Cliente deletato com sucesso do banco de dados local. " +
                    "Cliente não possui ID para api externa.");
        }
        integrationPaymentApi.deleteCustomer(customer.get().getIdApiExternal());
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findByCpf(String cpf) {

        Optional<Customer> queryCustomer = customerRepository.findByCpf(cpf);
        if (queryCustomer.isEmpty()) {
            throw new EntityNotFoundException("Cliente não encontrado!");
        }
        return queryCustomer.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findByName(String name) {

        List<Customer> results = customerRepository.findByName(name);
        if (results.isEmpty()) {
            throw new EntityNotFoundException("Nenhum resultado encontrado!");
        }
        return results;
    }

    @Transactional(readOnly = true)
    public List<CustomerCreatedDTO> findAll() {

        List<Customer> list = customerRepository.findAll();
        if (list.isEmpty()) {
            throw new EntityNotFoundException("Lista vazia.");
        }

        List<CustomerCreatedDTO> listDTO = new ArrayList<>();
        for (Customer customer : list) {
            CustomerCreatedDTO dto = new CustomerCreatedDTO();
            dto.setName(customer.getName());
            dto.setCpfCnpj(customer.getCpf());
            dto.setEmail(customer.getEmail());
            dto.setMobilePhone(customer.getPhoneNumber());
            listDTO.add(dto);
        }
        return listDTO;
    }
}




