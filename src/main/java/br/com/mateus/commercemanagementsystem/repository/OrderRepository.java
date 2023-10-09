package br.com.mateus.commercemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateus.commercemanagementsystem.model.Order;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
