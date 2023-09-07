package br.com.mateus.commercemanagementsystem;

import br.com.mateus.commercemanagementsystem.model.*;
import br.com.mateus.commercemanagementsystem.model.enums.Categories;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
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
												  OrderItemRepository orderItemRepository) {
        return (args) -> {

			// create objects

			LocalDate birthdate = LocalDate.of(2023, 12, 15);
			Client mateus = new Client("Mateus", birthdate, "12345678900", "1291978003", "endereço");

			LocalDateTime date = LocalDateTime.now();
			Payment payment = new Payment(PaymentType.BOLETO, 1258L, new BigDecimal("1500.00"), date,
					PaymentStatus.COMPLETED, mateus, null);

			OrderItem item1 = new OrderItem("playstation", 3, new BigDecimal("4500.00"));

			Product playstation = new Product(1122L, "playstation", new BigDecimal("1500.00"),
					80, Categories.ELETRONICS);

			// create list with product and add list to order

			List<OrderItem> orderItems = new ArrayList<OrderItem>();
			orderItems.add(item1);

			Order order = new Order(1329L, new BigDecimal("1500.00"), payment, mateus, orderItems);

			// set order in payment
			payment.setOrder(order);

			// persisting data
			clientRepository.save(mateus);
			orderRepository.save(order);
			orderItemRepository.save(item1);
			productRepository.save(playstation);
			paymentRepository.save(payment);
		};
	}
}
