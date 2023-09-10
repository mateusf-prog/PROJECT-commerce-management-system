package br.com.mateus.commercemanagementsystem;

import br.com.mateus.commercemanagementsystem.model.*;
import br.com.mateus.commercemanagementsystem.model.enums.Categories;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import br.com.mateus.commercemanagementsystem.service.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;

import br.com.mateus.commercemanagementsystem.repository.ClientRepository;
import br.com.mateus.commercemanagementsystem.repository.OrderItemRepository;
import br.com.mateus.commercemanagementsystem.repository.OrderRepository;
import br.com.mateus.commercemanagementsystem.repository.PaymentRepository;
import br.com.mateus.commercemanagementsystem.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CommerceManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommerceManagementSystemApplication.class, args);
	}

	@Bean
    public CommandLineRunner myCommandLineRunner (ClientRepository clientRepository, OrderRepository orderRepository,
												  PaymentRepository paymentRepository, ProductRepository productRepository,
												  OrderItemRepository orderItemRepository, ClientService clientService) {
        return (args) -> {

			// create objects

			LocalDate birthdate = LocalDate.of(2023, 12, 15);
			Client mateus = new Client("Mateus", birthdate, "123445678900", "1291978003",
					"endereço");

			clientRepository.save(mateus);

			LocalDateTime date = LocalDateTime.now();
			Payment payment = new Payment(PaymentType.BOLETO, 7890L, new BigDecimal("1500.00"),
					date, PaymentStatus.COMPLETED, mateus, null);

			paymentRepository.save(payment);

			Product playstation = new Product(4687L, "playstation", new BigDecimal("1500.00"),
					80, Categories.ELETRONICS);

			productRepository.save(playstation);

			List<OrderItem> orderItems = new ArrayList<>();
			OrderItem item1 = new OrderItem("playstation", 3, new BigDecimal("4500.00"), null);
			orderItems.add(item1);

			Order order = new Order(7956L, new BigDecimal("1500.00"), payment, mateus, orderItems);

			item1.setOrder(order); // set order on OrderItem before persist order
			payment.setOrder(order); // set order on Payment before persist order
			orderRepository.save(order);
			orderItemRepository.save(item1);

			// test ClientService

			 clientService.deleteByCpf("123445678900");
		};
	}
}
