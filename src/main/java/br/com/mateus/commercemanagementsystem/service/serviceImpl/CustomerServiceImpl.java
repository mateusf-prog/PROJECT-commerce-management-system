package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
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

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer updateCustomer(Customer customer) {

        Optional<Customer> queryCustomer = customerRepository.findByCpf(customer.getCpf());

         if (queryCustomer.isEmpty()) {
             throw new EntityNotFoundException("Cliente não encontrado!");
         }

         customerRepository.save(customer);

         return customer;
    }

    @Override
    @Transactional
    public Customer createCustomer(Customer customer) {

        Optional<Customer> queryCustomer = customerRepository.findByCpf(customer.getCpf());

        if (queryCustomer.isPresent()) {
            throw new EntityAlreadyExistsException("CPF já cadastrado!");
        }

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




