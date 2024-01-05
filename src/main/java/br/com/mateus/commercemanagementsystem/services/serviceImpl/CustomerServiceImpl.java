package br.com.mateus.commercemanagementsystem.services.serviceImpl;

import br.com.mateus.commercemanagementsystem.dto.CustomerCreatedDTO;
import br.com.mateus.commercemanagementsystem.dto.CustomerDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.ResourceNotFoundException;
import br.com.mateus.commercemanagementsystem.integration.integrationImpl.CustomerApiServiceImpl;
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
    public Customer createCustomer(Customer customer) {

        Optional<Customer> queryCustomer = customerRepository.findByCpf(customer.getCpf());
        if (queryCustomer.isPresent()) {
            throw new EntityAlreadyExistsException("Cliente já cadastrado.");
        }

        customerRepository.save(customer);
        return customer;
    }

    @Override
    @Transactional
    public Customer updateCustomer(Customer customer) {

        Customer queryCustomer = customerRepository.findByCpf(customer.getCpf()).orElseThrow(
                () -> new ResourceNotFoundException("Cliente não encontrado"));

        if (queryCustomer.getIdApiExternal() != null) {
            integrationPaymentApi.updateCustomer(customer);
        }

        customerRepository.save(customer);
        return customer;
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findByCpf(String cpf) {

        Optional<Customer> queryCustomer = customerRepository.findByCpf(cpf);
        if (queryCustomer.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não encontrado!");
        }
        return queryCustomer.get();
    }

    @Transactional(readOnly = true)
    public List<CustomerCreatedDTO> findAll() {

        List<Customer> list = customerRepository.findAll();
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Lista vazia.");
        }

        List<CustomerCreatedDTO> listDTO = new ArrayList<>();
        for (Customer customer : list) {
            CustomerCreatedDTO dto = new CustomerCreatedDTO();
            dto.setName(customer.getName());
            dto.setCpf(customer.getCpf());
            dto.setEmail(customer.getEmail());
            dto.setPhoneNumber(customer.getPhoneNumber());
            listDTO.add(dto);
        }
        return listDTO;
    }
}




