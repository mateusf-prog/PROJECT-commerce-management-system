package br.com.mateus.commercemanagementsystem;

import br.com.mateus.commercemanagementsystem.dto.ProductDTO;
import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.repository.CategoryRepository;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIT {

    @Autowired
    private WebTestClient testClient;
    @Autowired
    private CategoryRepository categoryRepository;

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
    @Sql(scripts = "/sql/categories/categories-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/products/products-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/products/products-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "/sql/categories/categories-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getProduct_FindAll_ShouldReturn200() {

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
    @Sql(scripts = "/sql/products/products-delete.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getProduct_FindAllEmptyList_ShouldReturn404() {

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
}
