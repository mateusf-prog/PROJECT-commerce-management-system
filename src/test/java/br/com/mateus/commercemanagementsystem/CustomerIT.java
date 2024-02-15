package br.com.mateus.commercemanagementsystem;

import br.com.mateus.commercemanagementsystem.dto.CustomerCreatedOrUpdatedDTO;
import br.com.mateus.commercemanagementsystem.model.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Each time that run this test, it's create a customer in the external Api too
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/customers/customers-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/customers/customers-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CustomerIT {

    @Autowired
    private WebTestClient testClient;

    /**
     * This test verifies that a customer can be created successfully.
     */
    @Test
    public void createCustomer_WithAllValidAttributes_ShouldReturnCreateStatus201() {
        String birthDateString = "15/12/1997";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthdate = LocalDate.parse(birthDateString, fmt);

        CustomerCreatedOrUpdatedDTO response = testClient
                .post()
                .uri("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Customer("Mateus", birthdate, "49366058329",
                        "12991978003", "Rua BPL", "teste@gmail.com"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CustomerCreatedOrUpdatedDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo("Mateus");
    }

    /**
     * This test verifies if creation of entity without name return error 400
     */
    @Test
    public void createCustomer_WithoutName_ShouldReturnMessageStatus400() {
        String responseBody = testClient
                .post()
                .uri("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Customer("", LocalDate.of(1997, 12, 15), "49366058329",
                        "12991978003", "Rua BPL", "teste@gmail.com"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).contains("Nome não pode ficar em branco.");
    }

    /**
     * This test verifies if creation of entity without address return error 400
     */
    @Test
    public void createCustomer_WithoutAddress_ShouldReturnMessageStatus400() {
        String responseBody = testClient
                .post()
                .uri("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Customer("Mateus", LocalDate.of(1997, 12, 15), "49366058329",
                        "12991978003", "", "teste@gmail.com"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).contains("Endereço não pode ficar em branco.");
    }

    /**
     * This test verifies if creation of entity without email return error 400
     */
    @Test
    public void createCustomer_WithoutEmail_ShouldReturnMessageStatus400() {
        String responseBody = testClient
                .post()
                .uri("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Customer("Mateus", LocalDate.of(1997, 12, 15), "49366058329",
                        "12991978003", "Rua BPL, 170", ""))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).contains("Email não pode ficar em branco.");
    }

    /**
     * This test verifies if creation of entity without CPF return error 400
     */
    @Test
    public void createCustomer_WithoutCpf_ShouldReturnMessageStatus400() {
        String responseBody = testClient
                .post()
                .uri("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Customer("Mateus", LocalDate.of(1997, 12, 15), "",
                        "12991978003", "Rua BPL, 170", "teste@gmail.com"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).contains("CPF inválido.");
    }

    /**
     * This test verifies if creation of entity without phone number return error 400
     */
    @Test
    public void createCustomer_WithoutPhoneNumber_ShouldReturnMessageStatus400() {
        String responseBody = testClient
                .post()
                .uri("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Customer("Mateus", LocalDate.of(1997, 12, 15), "49366058329",
                        "", "Rua BPL, 170", "teste@gmail.com"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).contains("Número de telefone não pode ficar em branco.");
    }

    /**
     * This test verifies if creation of entity without birthdate number return error 400
     */
    @Test
    public void createCustomer_WithoutBirthdate_ShouldReturnMessageStatus400() {
        String responseBody = testClient
                .post()
                .uri("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Customer("Mateus", null, "49366058329",
                        "12991978003", "Rua BPL, 170", "teste@gmail.com"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).contains("Data de nascimento não pode ficar em branco.");
    }
}
