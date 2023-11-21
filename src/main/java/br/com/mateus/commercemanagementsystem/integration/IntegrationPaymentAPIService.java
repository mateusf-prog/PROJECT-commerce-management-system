package br.com.mateus.commercemanagementsystem.integration;

import br.com.mateus.commercemanagementsystem.integration.model.CustomerPaymentApi;
import br.com.mateus.commercemanagementsystem.integration.model.BillingRequest;
import br.com.mateus.commercemanagementsystem.model.Customer;

public interface IntegrationPaymentAPIService {

    CustomerPaymentApi createCustomer(Customer customer);
    CustomerPaymentApi readCustomerById(String id);
    CustomerPaymentApi updateCustomer(Customer customer);
    boolean deleteCustomer(String id);


    BillingRequest createBilling();
}
