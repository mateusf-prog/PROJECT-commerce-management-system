package br.com.mateus.commercemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateus.commercemanagementsystem.model.OrderItem;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
