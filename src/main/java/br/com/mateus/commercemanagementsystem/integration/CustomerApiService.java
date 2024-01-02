package br.com.mateus.commercemanagementsystem.integration;

import br.com.mateus.commercemanagementsystem.dto.CustomerDTO;
import br.com.mateus.commercemanagementsystem.integration.model.BillingRequest;
import br.com.mateus.commercemanagementsystem.model.Customer;

public interface CustomerApiService {

    CustomerDTO createCustomer(Customer customer);
    CustomerDTO findCustomer(String id);
    CustomerDTO updateCustomer(Customer customer);
    void deleteCustomer(String id);
}
