package br.com.mateus.commercemanagementsystem.integration;

import br.com.mateus.commercemanagementsystem.dto.CustomerDTO;
import br.com.mateus.commercemanagementsystem.integration.model.BillingRequest;
import br.com.mateus.commercemanagementsystem.model.Customer;

public interface IntegrationPaymentAPIService {

    CustomerDTO createCustomer(Customer customer);
    CustomerDTO findCustomer(String id);
    CustomerDTO updateCustomer(Customer customer);
    boolean deleteCustomer(String id);


    BillingRequest createBilling();
}
