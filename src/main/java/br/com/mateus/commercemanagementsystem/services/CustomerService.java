package br.com.mateus.commercemanagementsystem.services;

import br.com.mateus.commercemanagementsystem.dto.CustomerCreatedOrUpdatedDTO;
import br.com.mateus.commercemanagementsystem.model.Customer;

public interface CustomerService {

    CustomerCreatedOrUpdatedDTO updateCustomer(Customer customer);
    CustomerCreatedOrUpdatedDTO createCustomer(Customer customer);
    Customer findByCpf(String cpf);
}




