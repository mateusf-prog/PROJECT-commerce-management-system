package br.com.mateus.commercemanagementsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class that configures the OpenAPI documentation
 */

@Configuration
public class SpringDocsOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Commerce Management System")
                                .description("The merchant management system allows merchants to manage their sales, stocks, payments and customers. " +
                                        "Integration with a payment API via (boleto or PIX) and after the purchase the customer receives the purchase data via email.")
                                .version("v1")
                                .contact(new Contact()
                                        .name("Mateus")
                                        .email("mateus102006@hotmail.com"))
                );
    }
}
