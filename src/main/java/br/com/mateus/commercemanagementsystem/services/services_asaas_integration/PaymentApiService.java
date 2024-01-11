package br.com.mateus.commercemanagementsystem.services.services_asaas_integration;

import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.model_asaas_integration.BillingResponse;

public interface PaymentApiService {

    BillingResponse createPayment(Payment payment);
}
