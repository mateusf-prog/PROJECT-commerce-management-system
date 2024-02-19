package br.com.mateus.commercemanagementsystem;

import br.com.mateus.commercemanagementsystem.dto.OrderItemDTO;
import br.com.mateus.commercemanagementsystem.dto.PaymentPostDTO;
import br.com.mateus.commercemanagementsystem.dto.PaymentReturnDTO;
import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.model.Customer;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.model.enums.OrderStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import br.com.mateus.commercemanagementsystem.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentIT {

    @Autowired
    private WebTestClient testClient;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    private Order order;

    // For this test, the customer's CPF was manually entered into the external API.
    @BeforeEach
    public void setUp() {

        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
        customerRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        Category category = new Category("Electronics");
        category = categoryRepository.save(category);
        Product product = new Product("Computer", BigDecimal.valueOf(5.0), 100, category);
        product = productRepository.save(product);
        Customer customer = new Customer("Mateus", LocalDate.of(1997, 12, 15),
                "60017663040", "12991978003", "Rua BPL", "teste@hotmail.com");
        customer.setIdApiExternal("cus_000005886331");
        customer = customerRepository.save(customer);

        List<OrderItemDTO> items = new ArrayList<>();
        items.add(new OrderItemDTO(product.getId(), 2));
        order = new Order(BigDecimal.valueOf(10.0), null, customer, Instant.now(), OrderStatus.WAITING_PAYMENT);
        order = orderRepository.save(order);
    }

    @Test
    public void createPayment_WithAllValidAttributes_ShouldReturnCreateStatus201() {

        PaymentReturnDTO response = testClient
                .post()
                .uri("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PaymentPostDTO(order.getId(), PaymentType.BOLETO))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PaymentReturnDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
    }
}
