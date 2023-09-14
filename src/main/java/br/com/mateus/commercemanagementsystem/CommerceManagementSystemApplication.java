package br.com.mateus.commercemanagementsystem;

import br.com.mateus.commercemanagementsystem.model.*;
import br.com.mateus.commercemanagementsystem.model.enums.Categories;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import br.com.mateus.commercemanagementsystem.service.serviceImpl.ClientServiceImpl;
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
												  OrderItemRepository orderItemRepository, ClientServiceImpl clientService) {
        return (args) -> {

			// create objects

			/*LocalDate birthdate = LocalDate.of(2023, 12, 15);
			Client mateus = new Client("Mateus", birthdate, "123445678900", "1291978003",
					"endereço");
			Client diogo = new Client("Diogo", birthdate, "615649798765", "123456789",
					"endereçodiogo");
			Client lucas = new Client("Lucas", birthdate, "312364979878", "000000000",
					"endereçolucas");

			clientRepository.save(mateus);
			clientRepository.save(diogo);
			clientRepository.save(lucas);

			LocalDateTime date = LocalDateTime.now();
			Payment paymentMateus = new Payment(PaymentType.BOLETO, 7890L, new BigDecimal("1500.00"),
					date, PaymentStatus.COMPLETED, mateus, null);
			Payment paymentDiogo = new Payment(PaymentType.PIX, 2390L, new BigDecimal("1500.00"),
					date, PaymentStatus.COMPLETED, diogo, null);
			Payment paymentLucas = new Payment(PaymentType.BOLETO, 9877L, new BigDecimal("1500.00"),
					date, PaymentStatus.PENDING, lucas, null);

			paymentRepository.save(paymentMateus);
			paymentRepository.save(paymentDiogo);
			paymentRepository.save(paymentLucas);

			Product playstation = new Product(4687L, "playstation", new BigDecimal("1500.00"),
					5, Categories.ELETRONICS);
			Product camiseta = new Product(7897L, "camiseta", new BigDecimal("100.00"),
					80, Categories.CLOTHING);
			Product macbook = new Product(1254L, "macbook", new BigDecimal("5000.00"),
					50, Categories.ELETRONICS);

			productRepository.save(playstation);
			productRepository.save(camiseta);
			productRepository.save(macbook);

			List<OrderItem> orderItems = new ArrayList<>();
			OrderItem item1 = new OrderItem("playstation", 3, new BigDecimal("4500.00"), null);
			OrderItem item2 = new OrderItem("camiseta", 3, new BigDecimal("300.00"), null);
			OrderItem item3 = new OrderItem("macbook", 3, new BigDecimal("15000.00"), null);
			orderItems.add(item1);
			orderItems.add(item2);
			orderItems.add(item3);

			Order order = new Order(7956L, new BigDecimal("1500.00"), paymentMateus, mateus, orderItems);
			Order order2 = new Order(4562L, new BigDecimal("1500.00"), paymentDiogo, mateus, orderItems);
			Order order3 = new Order(7842L, new BigDecimal("1500.00"), paymentLucas, lucas, orderItems);

			item1.setOrder(order); // set order on OrderItem before persist order
			item2.setOrder(order2);
			item3.setOrder(order3);

			paymentMateus.setOrder(order);
			paymentDiogo.setOrder(order2);
			paymentLucas.setOrder(order3);

			orderRepository.save(order);
			orderRepository.save(order2);
			orderRepository.save(order3);

			orderItemRepository.save(item1);
			orderItemRepository.save(item2);
			orderItemRepository.save(item3);*/

			LocalDate birthdate = LocalDate.of(2000, 10, 25);
			Client client = new Client("José", birthdate, "84484564698", "89547856987",
					"Rua 123");

			System.out.println(clientService.findByName("José"));

			System.out.println("Encontrar por nome: José");
			clientService.findByName("José");

		};
	}
}
