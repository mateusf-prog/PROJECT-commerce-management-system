package br.com.mateus.commercemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateus.commercemanagementsystem.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    
}
