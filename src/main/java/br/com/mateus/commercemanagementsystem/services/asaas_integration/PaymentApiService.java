package br.com.mateus.commercemanagementsystem.services.asaas_integration;

import br.com.mateus.commercemanagementsystem.exceptions.ExternalApiException;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import br.com.mateus.commercemanagementsystem.model.model_asaas_integration.BillingRequest;
import br.com.mateus.commercemanagementsystem.model.model_asaas_integration.BillingResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
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

        BillingRequest billingRequest = new BillingRequest();
        billingRequest.setCustomer(payment.getOrder().getCustomer().getIdApiExternal());
        billingRequest.setBillingType(payment.getPaymentType().name());
        billingRequest.setDescription("ORDER ID: " + payment.getOrder().getId());
        billingRequest.setValue(payment.getValue().doubleValue());
        billingRequest.setDueDate(getDueDate(payment, payment.getPaymentType()).toString());

        try {
            HttpEntity<BillingRequest> entity = new HttpEntity<>(billingRequest, headers());
            ResponseEntity<BillingResponse> response = restTemplate.exchange(
                    url + "/",
                    HttpMethod.POST,
                    entity,
                    BillingResponse.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new ExternalApiException("Não foi possível processar a solicitação. Erro de autorização com a API");
            }
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new ExternalApiException("Limite de cobranças na API ASAAS excedido");
            }
            throw new RuntimeException("Erro ao criar pagamento: " + e.getMessage());
        } catch (RestClientException e) {
            throw new RuntimeException("Erro ao criar pagamento: " + e.getMessage());
        }
    }

    public BillingResponse findById(String id) {

        try {
            HttpEntity<String> entity = new HttpEntity<>(id, headers());
            ResponseEntity<BillingResponse> response = restTemplate.exchange(
                    url + "/",
                    HttpMethod.GET,
                    entity,
                    BillingResponse.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new ExternalApiException("Não foi possível processar a solicitação. Erro de autorização com a API");
            }
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ExternalApiException("Pagamento não encontrado na API de pagamentos");
            }
        }
        return null;
    }

    public BillingResponse cancel(String id) {

        try {
            HttpEntity<String> entity = new HttpEntity<>(id, headers());
            ResponseEntity<BillingResponse> response = restTemplate.exchange(
                    url + "/",
                    HttpMethod.DELETE,
                    entity,
                    BillingResponse.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new ExternalApiException("Não foi possível processar a solicitação. Erro de autorização com a API");
            }
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ExternalApiException("Pagamento não encontrado na API de pagamentos");
            }
        }
        return null;
    }

    private static LocalDate getDueDate(Payment payment, PaymentType type) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime dueDate = null;

        if (PaymentType.PIX.name().equals(payment.getOrder().getPayment().getPaymentType().name())) {
            dueDate = LocalDateTime.ofInstant(payment.getMoment(), zoneId).plusMinutes(30);
        }
        if (PaymentType.BOLETO.name().equals(payment.getOrder().getPayment().getPaymentType().name())) {
            dueDate = LocalDateTime.ofInstant(payment.getMoment(), zoneId).plusDays(3);
        }
        return dueDate.toLocalDate();
    }

    private HttpHeaders headers() {
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.set("access_token", token);
        return headers;
    }
}
