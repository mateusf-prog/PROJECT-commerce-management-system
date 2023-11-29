package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.dto.CustomerDTO;
import br.com.mateus.commercemanagementsystem.model.Customer;

import java.util.List;

public interface CustomerService {

    Customer updateCustomer(Customer customer);
    Customer createCustomer(Customer customer);
    void deleteByCpf(String cpf);
    Customer findByCpf(String cpf);
    List<Customer> findByName(String name);
}




