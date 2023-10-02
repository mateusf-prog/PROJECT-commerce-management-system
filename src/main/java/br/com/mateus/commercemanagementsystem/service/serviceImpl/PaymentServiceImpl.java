package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import br.com.mateus.commercemanagementsystem.repository.PaymentRepository;
import br.com.mateus.commercemanagementsystem.service.PaymentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment createPayment(Payment payment) {

        paymentRepository.save(payment);

        return payment;
    }

    @Override
    @Transactional
    public Payment updatePayment(Payment payment) {

        Optional<Payment> paymentExists = paymentRepository.findById(payment.getId());

        if(paymentExists.isEmpty()) {
            throw new EntityNotFoundException("Pagamento não encontrado - ID " + payment.getId());
        }

        return payment;
    }

    @Override
    public Payment readPayment(Payment payment) {
        return null;
    }

    @Override
    public String deleteById(Long id) {
        return null;
    }

    @Override
    public String changePaymentType(Payment payment, PaymentType type) {
        return null;
    }

    @Override
    public String changePaymentStatus(Payment payment, PaymentStatus status) {
        return null;
    }

    @Override
    public String sendToEmail(Payment payment, String email) {
        return null;
    }

    @Override
    public Payment processPayment(Payment payment) {
        return null;
    }

    @Override
    public PaymentStatus callPaymentApi(Payment payment) {
        return null;
    }

    @Override
    public boolean validateExistenceOrderInPayment(Payment payment) {
        return false;
    }
}