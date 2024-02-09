package br.com.mateus.commercemanagementsystem;

import br.com.mateus.commercemanagementsystem.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderIT {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void createOrder_WithAllValidAttributes_ShouldReturnCreateStatus201() {

    }
}
