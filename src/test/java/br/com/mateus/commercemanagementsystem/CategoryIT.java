package br.com.mateus.commercemanagementsystem;

import br.com.mateus.commercemanagementsystem.dto.CategoryDTO;
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
    public void createCategory_WithName_ShouldReturnCreated() {
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
}
