package br.com.mateus.commercemanagementsystem.integration;

import br.com.mateus.commercemanagementsystem.dto.CustomerDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.integration.model.BillingRequest;
import br.com.mateus.commercemanagementsystem.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
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

        // define headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("access_token", token);

        // create entity
        HttpEntity<CustomerDTO> entity = new HttpEntity<>(customerDTO, headers);

        // call API
        ResponseEntity<CustomerDTO> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                CustomerDTO.class
        );

        if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
            customerDTO.setId(response.getBody().getId());
            return response.getBody();
        } else {
            throw new EntityInvalidDataException("Erro ao criar cliente na API externa");
        }
    }

    @Override
    public void findCustomer(String id) {
        // TODO implementar a busca de um cliente na api
    }

    @Override
    public void updateCustomer(Customer customer) {
        // TODO implementar a atualização de um cliente na api quando o cliente é atualizado no banco local
    }

    @Override
    public boolean deleteCustomer(String id) {
        // TODO implementar a exclusão de um cliente na api quando excluido no banco local
    }

    @Override
    public BillingRequest createBilling() {
        return null;
    }

    public CustomerDTO convertCustomerToCustomerDTO(Customer customer) {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(customer.getName());
        customerDTO.setCpfCnpj(customer.getCpf());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setMobilePhone(customer.getPhoneNumber());
        return customerDTO;
    }
}
