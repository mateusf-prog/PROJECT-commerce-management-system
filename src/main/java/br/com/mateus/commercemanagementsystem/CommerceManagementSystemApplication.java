package br.com.mateus.commercemanagementsystem;

import br.com.mateus.commercemanagementsystem.model.Customer;
import br.com.mateus.commercemanagementsystem.repository.CustomerRepository;
import br.com.mateus.commercemanagementsystem.services.CustomerService;
import br.com.mateus.commercemanagementsystem.services.asaas_integration.CustomerApiService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class CommerceManagementSystemApplication{

	public static void main(String[] args) {
		SpringApplication.run(CommerceManagementSystemApplication.class, args);
	}
}

