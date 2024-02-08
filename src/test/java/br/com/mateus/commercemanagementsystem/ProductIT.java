package br.com.mateus.commercemanagementsystem;

import br.com.mateus.commercemanagementsystem.dto.ProductDTO;
import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.repository.CategoryRepository;
import br.com.mateus.commercemanagementsystem.repository.ProductRepository;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIT {

    @Autowired
    private WebTestClient testClient;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    /**
     * seeding a database before each test
     */
    @BeforeEach
    public void setup() {
        Category category1 = new Category("Livros");
        Category category2 = new Category("Eletronicos");
        Category category3 = new Category("Roupas");

        category1 = categoryRepository.save(category1);
        category2 = categoryRepository.save(category2);
        category3 = categoryRepository.save(category3);

        Product product1 = new Product("Computer", BigDecimal.valueOf(1500), 100, category2);
        Product product2 = new Product("SmartPhone", BigDecimal.valueOf(1000), 80, category2);
        Product product3 = new Product("Harry Potter", BigDecimal.valueOf(80), 50, category1);
        Product product4 = new Product("The Lord of the Kings", BigDecimal.valueOf(50), 50, category1);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
    }

    /**
     * cleaning a database after each tests
     */
    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    /**
     * This test verifies that a product can be created successfully.
     */
    @Test
    public void createProduct_ShouldReturnCreatedStatus201() {

        Category category = new Category("Smartphones");
        category = categoryRepository.save(category);

        ProductDTO response = testClient
                .post()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Product("Galaxy", new BigDecimal("1200.00"), 150, category))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProductDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo("Galaxy");
    }

    /**
     * This test verifies if a product without name return an error status 400
     */
    @Test
    public void createProduct_WithoutName_ShouldReturnStatus400() {

        Category category = new Category("Smartphones");
        category = categoryRepository.save(category);

        String response = testClient
                .post()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Product("", new BigDecimal("1200.00"), 150, category))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
    }

    /**
     * This test verifies if a product with wrong price return an error status 400
     */
    @Test
    public void createProduct_WithWrongPrice_ShouldReturnStatus400() {

        Category category = new Category("Smartphones");
        category = categoryRepository.save(category);

        String response = testClient
                .post()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Product("iPhone", new BigDecimal("0.00"), 150, category))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
    }

    /**
     * This test verifies if a product with '0' quantity return an error status 400
     */
    @Test
    public void createProduct_WithoutCategory_ShouldReturnStatus400() {

        String response = testClient
                .post()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Product("iPhone", new BigDecimal("0.00"), 150, null))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
    }

    /**
     * This test verifies if a list of products existent return status 200 OK
     */
    @Test
    public void getProduct_FindAll_ShouldReturnStatus200() {

        String response = testClient
                .get()
                .uri("/api/products")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
    }

    /**
     * This test verifies if an empty list of products return status 404
     */
    @Test
    public void getProduct_FindAllEmptyList_ShouldReturnStatus404() {

        productRepository.deleteAll();

        String response = testClient
                .get()
                .uri("/api/products")
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        String errorMessage = JsonPath.read(response, "$.message");

        Assertions.assertThat(errorMessage).isEqualTo("Lista de produtos vazia!");
    }

    /**
     * This test verifies that the search for a product by name return status 200
     */
    @Test
    public void getProduct_FindByName_ShouldReturnStatus200() {

        ProductDTO response = testClient
                .get()
                .uri("/api/products/name/Computer")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(ProductDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo("Computer");
    }

    /**
     * This test verifies that the search for a product by id return status 200
     */
    @Test
    public void getProduct_FindById_ShouldReturnStatus200() {

        ProductDTO response = testClient
                .get()
                .uri("/api/products/1")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(ProductDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo("Computer");
    }

    /**
     * This test verifies that the search for a product by id with non-existent id return status 404
     */
    @Test
    public void getProduct_FindByIdWithNonExistentId_ShouldReturnStatus404() {

        String response = testClient
                .get()
                .uri("/api/products/10")
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
    }

    /**
     * This test verifies that the search for a product by id with non-existent name return status 404
     */
    @Test
    public void getProduct_FindByIdWithNonExistentName_ShouldReturnStatus404() {

        String response = testClient
                .get()
                .uri("/api/products/name/Tablet")
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
    }

    /**
     * This test verifies if the product update return a status 200
     */
    @Test
    public void updateProduct_ShouldReturnStatus200() {

        // create an entity before test
        Category category = new Category("Conputers");
        categoryRepository.save(category);
        Product product = new Product("Computer AMD", BigDecimal.valueOf(1500), 20, category);
        product = productRepository.save(product);

        ProductDTO response = testClient
                .put()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(ProductDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo("Computer AMD");
    }

    /**
     * This test verifies if the product update non-existent id return a status 404
     */
    @Test
    public void updateProduct_WithNonExistentId_ShouldReturnStatus400() {

        // create an entity before test
        Category category = new Category("Conputers");
        categoryRepository.save(category);
        Product product = new Product("Computer AMD", BigDecimal.valueOf(1500), 20, category);
        product = productRepository.save(product);

        product.setId(50L);

        String response = testClient
                .put()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
    }

    /**
     * This test verifies if the product update null id return a status 400
     */
    @Test
    public void updateProduct_WithNullId_ShouldReturnStatus400() {

        // create an entity before test
        Category category = new Category("Computers");
        categoryRepository.save(category);
        Product product = new Product("Computer AMD", BigDecimal.valueOf(1500), 20, category);

        String response = testClient
                .put()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        String errorMessage = JsonPath.read(response, "$.message");
        Assertions.assertThat(errorMessage).isEqualTo("O ID não pode ser nulo");
    }
}
