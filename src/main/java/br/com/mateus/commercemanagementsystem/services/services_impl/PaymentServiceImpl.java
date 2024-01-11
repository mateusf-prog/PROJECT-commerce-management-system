package br.com.mateus.commercemanagementsystem.services.services_impl;

import br.com.mateus.commercemanagementsystem.exceptions.ResourceNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import br.com.mateus.commercemanagementsystem.repository.PaymentRepository;
import br.com.mateus.commercemanagementsystem.services.PaymentService;
import br.com.mateus.commercemanagementsystem.services.services_asaas_integration.impl.PaymentApiServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentApiServiceImpl paymentApiService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentApiServiceImpl paymentApiService) {
        this.paymentRepository = paymentRepository;
        this.paymentApiService = paymentApiService;
    }

    @Override
    @Transactional
    public Payment createPayment(Order order) {

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setValue(order.getTotalValue());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentType(order.getPayment().getPaymentType());
        // TODO: paymentype retorna null porque há um loop, como saber o tipo de pagamento se o pagamento é criado aqui ?

        String id = paymentApiService.createPayment(payment).getId();
        payment.setIdApiExternal(id);

        return payment;
    }

    @Override
    @Transactional
    public Payment updatePayment(Payment payment) {

        Optional<Payment> paymentExists = paymentRepository.findById(payment.getId());

        if(paymentExists.isEmpty()) {
            throw new ResourceNotFoundException("Pagamento não encontrado - ID " + payment.getId());
        }

        paymentRepository.save(payment);

        return payment;
    }

    @Override
    @Transactional(readOnly = true)
    public Payment findById(Long id) {

        Optional<Payment> paymentExists = paymentRepository.findById(id);

        if(paymentExists.isEmpty()) {
            throw new ResourceNotFoundException("Pagamento não encontrado - ID " + id);
        }

        return paymentExists.get();
    }

    @Override
    @Transactional
    public String deleteById(Long id) {
        // todo: implementar
        return null;
    }

    @Override
    public String changePaymentType(Payment payment, PaymentType type) {
        // todo: implementar
        return null;
    }

    @Override
    public String changePaymentStatus(Payment payment, PaymentStatus status) {
        // todo: implementar
        return null;
    }

    @Override
    public String sendToEmail(Payment payment, String email) {
        // todo: implementar
        return null;
    }
}
