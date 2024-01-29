package br.com.mateus.commercemanagementsystem;

import br.com.mateus.commercemanagementsystem.dto.CategoryDTO;
import br.com.mateus.commercemanagementsystem.model.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/categories/categories-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/categories/categories-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CategoryIT {

    @Autowired
    public WebTestClient testClient;

    /**
     * This test verifies that a category can be created successfully.
     */
    @Test
    public void createCategory_WithName_ShouldReturnCreatedStatus201() {
        CategoryDTO response = testClient
                .post()
                .uri("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CategoryDTO(null, "Electronics"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CategoryDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo("Electronics");
    }

    /**
     * This test verifies that attempting to create a category without a name results in an error.
     */
    @Test
    public void createCategory_WithoutName_ShouldReturnMessageStatus400() {
        String responseBody = testClient
                .post()
                .uri("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CategoryDTO(null, ""))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).contains("Nome da categoria não pode ficar em branco!");
    }

    /**
     * This test verifies that attempting to create a category that already exists results in an error 409.
     */
    @Test
    public void createCategory_WithAlreadyExists_ShouldReturnMessageStatus409() {
        String responseBody = testClient
                .post()
                .uri("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CategoryDTO(null, "Livros"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).contains("Categoria já cadastrada. Nome: Livros");
    }

    /**
     * This test verifies if a category is found by name.
     */
    @Test
    public void getCategory_WithName_ShouldReturnStatus200() {
        Category response = testClient
                .get()
                .uri("/api/categories/Livros")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Category.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo("Livros");
    }

    /**
     * This test verifies that attempting to found a category that not exists results in an error 404.
     */
    @Test
    public void getCategory_NotExists_ShouldReturnStatus404() {
        String response = testClient
                .get()
                .uri("/api/categories/Sporting")
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isEqualTo("{\"message\":\"Categoria não encontrada. Nome: Sporting\",\"status\":404}");
    }
}
