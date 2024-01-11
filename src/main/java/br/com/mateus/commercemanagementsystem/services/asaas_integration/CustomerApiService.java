package br.com.mateus.commercemanagementsystem.services.asaas_integration;

import br.com.mateus.commercemanagementsystem.dto.CustomerDTO;
import br.com.mateus.commercemanagementsystem.model.Customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerApiService{

    @Value("https://sandbox.asaas.com/api/v3/customers")
    private String url;
    @Value("${asaas.token}")
    private String token;

    private final RestTemplate restTemplate = new RestTemplate();

    public CustomerApiService() {
    }

    public CustomerDTO createCustomer(Customer customer) {

        // TODO: implementar repetição 
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

    public CustomerDTO updateCustomer(Customer customer) {

        CustomerDTO customerDTO = null;

        if (customer.getIdApiExternal() == null) {
            customerDTO = createCustomer(customer);
            return customerDTO;
        }
        
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

    public CustomerDTO convertCustomerToCustomerDTO(Customer customer) {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getIdApiExternal());
        customerDTO.setName(customer.getName());
        customerDTO.setCpfCnpj(customer.getCpf());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        return customerDTO;
    }

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("access_token", token);
        return headers;
    }
}
