package br.com.mateus.commercemanagementsystem.integration;

import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.integration.model.BillingRequest;
import br.com.mateus.commercemanagementsystem.integration.model.CustomerPaymentApi;
import br.com.mateus.commercemanagementsystem.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IntegrationPaymentApiImpl implements IntegrationPaymentAPIService {

    @Value("${asaas.url}")
    private String url;
    @Value("${asaas.token}")
    private String token;
    private final RestTemplate restTemplate = new RestTemplate();

    public IntegrationPaymentApiImpl() {
    }

    @Override
    public CustomerPaymentApi createCustomer(Customer customer) {

        CustomerPaymentApi customerPaymentApi = new CustomerPaymentApi();

        customerPaymentApi.setName(customer.getName());
        customerPaymentApi.setCpfCnpj(customer.getCpf());
        customerPaymentApi.setEmail(customer.getEmail());
        customerPaymentApi.setMobilePhone(customer.getPhoneNumber());

        // define headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("access_token", token);

        // create entity
        HttpEntity<CustomerPaymentApi> entity = new HttpEntity<>(customerPaymentApi, headers);

        // call API
        ResponseEntity<CustomerPaymentApi> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                CustomerPaymentApi.class
        );

        if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
            return response.getBody();
        } else {
            throw new EntityInvalidDataException("Erro ao criar cliente!");
        }
    }

    @Override
    public CustomerPaymentApi readCustomerById(String id) {
        return null;
    }

    @Override
    public CustomerPaymentApi updateCustomer(Customer customer) {
        return null;
    }

    @Override
    public boolean deleteCustomer(String id) {
        return false;
    }

    @Override
    public BillingRequest createBilling() {
        return null;
    }
}
