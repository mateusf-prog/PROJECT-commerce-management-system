package br.com.mateus.commercemanagementsystem.integration.integrationImpl;

import br.com.mateus.commercemanagementsystem.integration.PaymentApiService;
import br.com.mateus.commercemanagementsystem.integration.model.BillingRequest;
import br.com.mateus.commercemanagementsystem.integration.model.BillingResponse;
import br.com.mateus.commercemanagementsystem.model.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class PaymentApiServiceImpl implements PaymentApiService {

    @Value("https://sandbox.asaas.com/api/v3/payments")
    private String url;
    @Value("${asaas.token}")
    private String token;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public BillingResponse createPayment(Payment payment) {

        BillingRequest billingRequest = new BillingRequest();
        billingRequest.setCustomer(payment.getOrder().getCustomer().getIdApiExternal());
        billingRequest.setBillingType(payment.getPaymentType().name());
        billingRequest.setDescription("ORDER " + payment.getOrder().getId());
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

        if ("PIX".equals(payment.getOrder().getPayment().getPaymentType().toString())) {
            billingRequest.setDueDate(LocalDate.from(payment.getDate().plusMinutes(10)));
        }
        if ("BOLETO".equals(payment.getOrder().getPayment().getPaymentType().toString())) {
            billingRequest.setDueDate(LocalDate.from(payment.getDate().plusDays(1)));
        }
        return billingRequest.getDueDate();
    }

    private org.springframework.http.HttpHeaders headers() {
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.set("access_token", token);
        return headers;
    }
}
