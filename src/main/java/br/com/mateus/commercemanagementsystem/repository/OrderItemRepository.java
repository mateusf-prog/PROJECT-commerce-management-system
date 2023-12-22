package br.com.mateus.commercemanagementsystem.repository;

import br.com.mateus.commercemanagementsystem.model.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateus.commercemanagementsystem.model.OrderItem;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Optional<OrderItem> findById(OrderItemPK id);
}
