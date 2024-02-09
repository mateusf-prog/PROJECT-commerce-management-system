package br.com.mateus.commercemanagementsystem;

import br.com.mateus.commercemanagementsystem.dto.OrderCreatedDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderItemDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderPostDTO;
import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.model.Customer;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.repository.*;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderIT {

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

    /**
     * Clear database before each test
     */
    @AfterEach
    public void tearDown() {
        orderItemRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        orderRepository.deleteAll();
        customerRepository.deleteAll();
    }

    /**
     * Seeding database before each test
     */
    @BeforeEach
    public void setUp() {

        Category category = new Category("Electronics");
        category = categoryRepository.save(category);
        Product product = new Product("Computer", BigDecimal.valueOf(2500.0), 100, category);
        product = productRepository.save(product);
        Customer customer = new Customer("Mateus", LocalDate.of(1997, 12, 15),
                "60017663040", "12991978003", "Rua BPL", "mateus102006@hotmail.com");
        customer = customerRepository.save(customer);
    }

    /**
     *  This test create an order with success, should return status 201
     */
    @Test
    public void createOrder_WithAllValidAttributes_ShouldReturnCreateStatus201() {

        Optional<Product> product = productRepository.findByName("Computer");
        List<OrderItemDTO> items = new ArrayList<>();
        items.add(new OrderItemDTO(product.get().getId(), 2));

        OrderCreatedDTO response = testClient
                .post()
                .uri("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new OrderPostDTO("60017663040", items))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(OrderCreatedDTO.class)
                .returnResult().getResponseBody();
    }

    /**
     *  This test create an order with invalid cpf, should return status 400 (CPF inválido)
     */
    @Test
    public void createOrder_InvalidCpf_ShouldReturnStatus400() {

        Optional<Product> product = productRepository.findByName("Computer");
        List<OrderItemDTO> items = new ArrayList<>();
        items.add(new OrderItemDTO(product.get().getId(), 2));

        String response = testClient
                .post()
                .uri("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new OrderPostDTO("12345678901", items))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        String errorMessage = JsonPath.read(response, "$.errors[0]");
        Assertions.assertThat(errorMessage).isEqualTo("CPF inválido");
    }

    /**
     *  This test create an order with empty list items, should return status 400 ("Lista de items não pode ser vazia")
     */
    @Test
    public void createOrder_WithEmptyListItems_ShouldReturnStatus400() {

        Optional<Product> product = productRepository.findByName("Computer");
        List<OrderItemDTO> items = new ArrayList<>();
        //items.add(new OrderItemDTO(product.get().getId(), 2));

        String response = testClient
                .post()
                .uri("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new OrderPostDTO("60017663040", items))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        String errorMessage = JsonPath.read(response, "$.errors[0]");
        Assertions.assertThat(errorMessage).isEqualTo("Lista de items não pode ser vazia");
    }

}
