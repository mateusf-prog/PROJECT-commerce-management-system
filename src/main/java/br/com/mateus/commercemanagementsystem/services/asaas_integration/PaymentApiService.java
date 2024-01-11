package br.com.mateus.commercemanagementsystem.services.asaas_integration;

import br.com.mateus.commercemanagementsystem.dto.CustomerDTO;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.model_asaas_integration.BillingRequest;
import br.com.mateus.commercemanagementsystem.model.model_asaas_integration.BillingResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class PaymentApiService {

    @Value("https://sandbox.asaas.com/api/v3/payments")
    private String url;
    @Value("${asaas.token}")
    private String token;
    private final RestTemplate restTemplate = new RestTemplate();
    private final CustomerApiService customerApiService;


    public PaymentApiService(CustomerApiService customerApiService) {
        this.customerApiService = customerApiService;
    }

    public BillingResponse createPayment(Payment payment) {

        CustomerDTO customerCreatedApi = new CustomerDTO();
        if (payment.getOrder().getCustomer().getIdApiExternal() == null) {
            customerCreatedApi = customerApiService.createCustomer(payment.getOrder().getCustomer());
        }

        BillingRequest billingRequest = new BillingRequest();
        billingRequest.setCustomer(customerCreatedApi.getId());
        billingRequest.setBillingType(payment.getPaymentType().name());
        billingRequest.setDescription("ORDER ID: " + payment.getOrder().getId());
        billingRequest.setValue(payment.getValue().doubleValue());
        billingRequest.setDueDate(getDueDate(payment, billingRequest));

        // define headers, create entity and call API
        HttpEntity<BillingRequest> entity = new HttpEntity<>(billingRequest, headers());
        ResponseEntity<BillingResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                BillingResponse.class
        );
        return response.getBody();
    }

    private static LocalDate getDueDate(Payment payment, BillingRequest billingRequest) {
        ZoneId zoneId = ZoneId.systemDefault();

        if ("PIX".equals(payment.getOrder().getPayment().getPaymentType().toString())) {
            LocalDateTime dueDate = LocalDateTime.ofInstant(payment.getMoment(), zoneId).plusMinutes(10);
            billingRequest.setDueDate(dueDate.toLocalDate());
        }
        if ("BOLETO".equals(payment.getOrder().getPayment().getPaymentType().toString())) {
            LocalDateTime dueDate = LocalDateTime.ofInstant(payment.getMoment(), zoneId).plusDays(1);
            billingRequest.setDueDate(dueDate.toLocalDate());
        }
        return billingRequest.getDueDate();
    }

    private org.springframework.http.HttpHeaders headers() {
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.set("access_token", token);
        return headers;
    }

    public BillingResponse cancelPayment(Payment payment) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelPayment'");
    }

    public BillingResponse setStatus(Payment payment) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStatus'");
    }
}
