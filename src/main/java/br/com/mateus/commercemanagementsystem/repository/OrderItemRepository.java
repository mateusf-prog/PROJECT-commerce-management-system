package br.com.mateus.commercemanagementsystem.repository;

import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateus.commercemanagementsystem.model.OrderItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Optional<OrderItem> findById(OrderItemPK id);

    @Query("SELECT i FROM OrderItem i WHERE i.id.order.id = :orderId")
    List<OrderItem> findByOrderId(@Param("orderId") Long orderId);

}
