package br.com.mateus.commercemanagementsystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import br.com.mateus.commercemanagementsystem.repository.ClientRepository;

@SpringBootApplication
public class CommerceManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommerceManagementSystemApplication.class, args);
	}

	@Bean
    public CommandLineRunner myCommandLineRunner(ClientRepository clientRepository) {
        return (args) -> {
			/*LocalDate birthDate = LocalDate.of(2023, 9, 3);
			Client client = new Client();
			client.setName("Mateus Fonseca");
			client.setCpf("12345678900");
			client.setBirthdate(birthDate);
			client.setAdress("Rua dos bugs, 404 - Sao Paulo-SP");
			client.setPhoneNumber("12991978003");

			clientRepository.save(client);*/

		};
	}
}
