package br.com.mateus.commercemanagementsystem.integration;

import br.com.mateus.commercemanagementsystem.integration.model.BillingResponse;
import br.com.mateus.commercemanagementsystem.model.Payment;

public interface PaymentApiService {

    BillingResponse createPayment(Payment payment);
}
