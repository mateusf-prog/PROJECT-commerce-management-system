package br.com.mateus.commercemanagementsystem.repository;

import br.com.mateus.commercemanagementsystem.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateus.commercemanagementsystem.model.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findPaymentsByClient(Client client);
}
