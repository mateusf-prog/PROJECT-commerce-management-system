package br.com.mateus.commercemanagementsystem.integration;

import br.com.mateus.commercemanagementsystem.integration.model.BillingRequest;
import br.com.mateus.commercemanagementsystem.integration.model.BillingResponse;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.Payment;

public interface PaymentApiService {

    BillingResponse createPayment(Payment payment);
}
