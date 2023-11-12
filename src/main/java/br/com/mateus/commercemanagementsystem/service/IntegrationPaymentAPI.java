package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.integration.model.BillingRequest;
import br.com.mateus.commercemanagementsystem.model.Customer;

public interface IntegrationPaymentAPI {

    Customer createCustomerInClientAPI(Customer customer);
    BillingRequest createBilling();
    
}
