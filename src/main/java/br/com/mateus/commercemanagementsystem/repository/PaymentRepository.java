package br.com.mateus.commercemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateus.commercemanagementsystem.model.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
