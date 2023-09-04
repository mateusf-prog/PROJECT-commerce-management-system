package br.com.mateus.commercemanagementsystem;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.mateus.commercemanagementsystem.model.Client;
import br.com.mateus.commercemanagementsystem.model.Commerce;
import br.com.mateus.commercemanagementsystem.repository.ClientRepository;
import br.com.mateus.commercemanagementsystem.repository.CommerceRepository;

@SpringBootApplication
public class CommerceManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommerceManagementSystemApplication.class, args);
	}

	@Bean
    public CommandLineRunner myCommandLineRunner(ClientRepository clientRepository, CommerceRepository commerceRepository) {
        return (args) -> {
			/*LocalDate birthDate = LocalDate.of(2023, 9, 3);
			Client client = new Client();
			client.setName("Mateus Fonseca");
			client.setCpf("12345678900");
			client.setBirthdate(birthDate);
			client.setAdress("Rua dos bugs, 404 - Sao Paulo-SP");
			client.setPhoneNumber("12991978003");

			clientRepository.save(client);*/

			Commerce commerce = new Commerce("Loja 1", "1236579541/0001-02", 
			"12345678978", "Rua dos Bugs, 401, Sao Paulo-SP");

			commerceRepository.save(commerce);
		};
	}
}
