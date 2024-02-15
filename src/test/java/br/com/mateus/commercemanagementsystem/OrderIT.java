package br.com.mateus.commercemanagementsystem;

import br.com.mateus.commercemanagementsystem.dto.OrderCreatedDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderItemDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderPostDTO;
import br.com.mateus.commercemanagementsystem.model.*;
import br.com.mateus.commercemanagementsystem.model.enums.OrderStatus;
import br.com.mateus.commercemanagementsystem.repository.*;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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

    private Category category;
    private Product product;
    private Customer customer;

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

        category = new Category("Electronics");
        category = categoryRepository.save(category);
        product = new Product("Computer", BigDecimal.valueOf(2500.0), 100, category);
        product = productRepository.save(product);
        customer = new Customer("Mateus", LocalDate.of(1997, 12, 15),
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
     *  This test create an order with non-existent customer, should return status 404
     */
    @Test
    public void createOrder_CustomerNotFound_ShouldReturnStatus404() {

        Optional<Product> product = productRepository.findByName("Computer");
        List<OrderItemDTO> items = new ArrayList<>();
        items.add(new OrderItemDTO(product.get().getId(), 2));

        String response = testClient
                .post()
                .uri("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new OrderPostDTO("76453694000", items))
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        String errorMessage = JsonPath.read(response, "$.message");
        Assertions.assertThat(errorMessage).isEqualTo("Cliente não encontrado: CPF: 76453694000");
    }

    /**
     *  This test create an order with invalid cpf, should return status 400
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
     *  This test create an order with empty list items, should return status 400
     */
    @Test
    public void createOrder_EmptyListItems_ShouldReturnStatus400() {

        Optional<Product> product = productRepository.findByName("Computer");
        List<OrderItemDTO> items = new ArrayList<>();

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

    /**
     *  This test create an order with invalid items quantity, should return status 400
     */
    @Test
    public void createOrder_InvalidItemsQuantity_ShouldReturnStatus400() {

        Optional<Product> product = productRepository.findByName("Computer");
        List<OrderItemDTO> items = new ArrayList<>();
        items.add(new OrderItemDTO(product.get().getId(), 0));

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
        String errorMessage = JsonPath.read(response, "$.message");
        Assertions.assertThat(errorMessage).isEqualTo("Quantidade de itens inválida!");
    }

    /**
     *  This test create an order with non-existent products ID, should return status 404
     */
    @Test
    public void createOrder_NonExistentProductID_ShouldReturnStatus404() {

        Optional<Product> product = productRepository.findByName("Computer");
        List<OrderItemDTO> items = new ArrayList<>();
        items.add(new OrderItemDTO(50L, 0));

        String response = testClient
                .post()
                .uri("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new OrderPostDTO("60017663040", items))
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        String errorMessage = JsonPath.read(response, "$.message");
        Assertions.assertThat(errorMessage).isEqualTo("Produto não encontrado. ID: 50");
    }

    /**
     *  This test create an order with Overstock quantity, should return status 400
     */
    @Test
    public void createOrder_UnavailableStockQuantity_ShouldReturnStatus400() {

        Optional<Product> product = productRepository.findByName("Computer");
        List<OrderItemDTO> items = new ArrayList<>();
        items.add(new OrderItemDTO(product.get().getId(), 110));

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
        String errorMessage = JsonPath.read(response, "$.message");
        Assertions.assertThat(errorMessage).isEqualTo("Quantidade indisponível no estoque. Produto: Computer" );
    }

    @Test
    public void getOrders_FindAll_ShouldReturnStatus200(){

        Order order = new Order(BigDecimal.valueOf(5000), null, customer, Instant.now(), OrderStatus.WAITING_PAYMENT);
        orderRepository.save(order);

        List<OrderDTO> response = testClient
                .get()
                .uri("/api/orders")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBodyList(OrderDTO.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void getOrders_FindAllEmptyList_ShouldReturnStatus400(){

        String response = testClient
                .get()
                .uri("/api/orders")
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        String errorMessage = JsonPath.read(response, "$.message");

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(errorMessage).isEqualTo("Lista vazia!");
    }

    @Test
    public void getOrders_FindByCpf_ShouldReturnStatus200(){

        Order order = new Order(BigDecimal.valueOf(5000), null, customer, Instant.now(), OrderStatus.WAITING_PAYMENT);
        orderRepository.save(order);

        List<OrderDTO> response = testClient
                .get()
                .uri("/api/orders/" + customer.getCpf())
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBodyList(OrderDTO.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void getOrders_FindByNonExistsCpf_ShouldReturnStatus404(){

        String response = testClient
                .get()
                .uri("/api/orders/25162354261")
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        String errorMessage = JsonPath.read(response, "$.message");
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(errorMessage).isEqualTo("Cliente não possui nenhum pedido. CPF: 25162354261");
    }

    @Test
    public void getOrder_FindById_ShouldReturnStatus200(){

        Order order = new Order(BigDecimal.valueOf(5000), null, customer, Instant.now(), OrderStatus.WAITING_PAYMENT);
        order = orderRepository.save(order);

        OrderDTO response = testClient
                .get()
                .uri("/api/orders/id/" + order.getId())
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(OrderDTO.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void getOrder_FindByNonExistsId_ShouldReturnStatus404(){

        String response = testClient
                .get()
                .uri("/api/orders/id/50")
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        String errorMessage = JsonPath.read(response, "$.message");
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(errorMessage).isEqualTo("Pedido não encontrado");
    }

    @Test
    public void cancelOrder_ShouldReturnStatus200() {

        Order order = new Order(BigDecimal.valueOf(5000), null, customer, Instant.now(), OrderStatus.WAITING_PAYMENT);
        order = orderRepository.save(order);

        OrderDTO response = testClient
                .patch()
                .uri("/api/orders/cancel/" + order.getId())
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(OrderDTO.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(OrderStatus.CANCELLED);
    }

    @Test
    public void cancelOrder_WithNonExistsIdShouldReturnStatus404() {

        String response = testClient
                .patch()
                .uri("/api/orders/cancel/15")
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        String errorMessage = JsonPath.read(response, "$.message");

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(errorMessage).isEqualTo("Pedido não encontrado");
    }
}
