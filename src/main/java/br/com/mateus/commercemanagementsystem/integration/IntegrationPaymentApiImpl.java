package br.com.mateus.commercemanagementsystem.integration;

import br.com.mateus.commercemanagementsystem.dto.CustomerDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.integration.model.BillingRequest;
import br.com.mateus.commercemanagementsystem.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class IntegrationPaymentApiImpl implements IntegrationPaymentAPIService {

    @Value("https://sandbox.asaas.com/api/v3/customers")
    private String url;
    @Value("${asaas.token}")
    private String token;

    private final RestTemplate restTemplate = new RestTemplate();

    public IntegrationPaymentApiImpl() {
    }

    @Override
    public CustomerDTO createCustomer(Customer customer) {

        // create customerDTO
        CustomerDTO customerDTO = convertCustomerToCustomerDTO(customer);

        // define headers, create entity and call API
        HttpEntity<CustomerDTO> entity = new HttpEntity<>(customerDTO, headers());
        ResponseEntity<CustomerDTO> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                CustomerDTO.class
        );
        return response.getBody();
    }

    @Override
    public CustomerDTO findCustomer(String id) {

        // create entity and call API
        try {
            HttpEntity<String> entity = new HttpEntity<>(headers());
            ResponseEntity<CustomerDTO> response = restTemplate.exchange(
                    url + "/" + id,
                    HttpMethod.GET,
                    entity,
                    CustomerDTO.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new EntityNotFoundException("Cliente atualizado na base de dados local e não encontrado na API integrada");
        }
    }

    @Override
    public CustomerDTO updateCustomer(Customer customer) {

        CustomerDTO customerDTO = null;

        if (customer.getIdApiExternal() == null) {
            customerDTO = createCustomer(customer);
            return customerDTO;
        } else {
            customerDTO = convertCustomerToCustomerDTO(customer);

            // create entity and call API
            HttpEntity<CustomerDTO> entity = new HttpEntity<>(customerDTO, headers());
            ResponseEntity<CustomerDTO> response = restTemplate.exchange(
                    url + "/" + customer.getIdApiExternal(),
                    HttpMethod.PUT,
                    entity,
                    CustomerDTO.class
            );
            return response.getBody();
        }
    }

    @Override
    public boolean deleteCustomer(String id) {

        try {
            HttpEntity<String> entity = new HttpEntity<>(id, headers());
            ResponseEntity<String> response = restTemplate.exchange(
                    url + "/" + id,
                    HttpMethod.DELETE,
                    entity,
                    String.class
            );
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            throw new EntityNotFoundException("Cliente não encontrado na API externar.");
        }
    }

    @Override
    public BillingRequest createBilling() {
        return null;
    }

    public CustomerDTO convertCustomerToCustomerDTO(Customer customer) {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getIdApiExternal());
        customerDTO.setName(customer.getName());
        customerDTO.setCpfCnpj(customer.getCpf());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setMobilePhone(customer.getPhoneNumber());
        return customerDTO;
    }

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("access_token", token);
        return headers;
    }
}
