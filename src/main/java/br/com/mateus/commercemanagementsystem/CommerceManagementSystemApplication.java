package br.com.mateus.commercemanagementsystem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.mateus.commercemanagementsystem.model.Client;
import br.com.mateus.commercemanagementsystem.model.Commerce;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.model.enums.Categories;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import br.com.mateus.commercemanagementsystem.repository.ClientRepository;
import br.com.mateus.commercemanagementsystem.repository.CommerceRepository;
import br.com.mateus.commercemanagementsystem.repository.OrderItemRepository;
import br.com.mateus.commercemanagementsystem.repository.OrderRepository;
import br.com.mateus.commercemanagementsystem.repository.PaymentRepository;
import br.com.mateus.commercemanagementsystem.repository.ProductRepository;

@SpringBootApplication
public class CommerceManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommerceManagementSystemApplication.class, args);
	}

	@Bean
    public CommandLineRunner myCommandLineRunner(ClientRepository clientRepository, CommerceRepository commerceRepository, 
					OrderRepository orderRepository, PaymentRepository paymentRepository, ProductRepository productRepository,
					OrderItemRepository orderItemRepository) {
        return (args) -> {
 
			// testing crud commerce

			Commerce commerce = new Commerce("Petshop", "12365793443541/0001-02", 
			"12345678978", "teste endereço loja");

			Commerce commerce1 = new Commerce("Mercado", "123612319541/0001-02", 
			"984949494651", "teste endereço loja");

			Commerce commerce2 = new Commerce("Barbearia", "1236512379541/0001-02", 
			"321656466456", "teste endereço loja");

			commerceRepository.save(commerce);
			commerceRepository.save(commerce1);
			commerceRepository.save(commerce2);


			// testing crud client

			LocalDate birthDate = LocalDate.of(2023, 9, 3);
			Client client = new Client("Rayllam", birthDate, "784569624554",
			"5566889966", "Rua 1, Belo Horizonte, MG", commerce);

			Client client2 = new Client("Diogo", birthDate, "88995566337",
			"2358749616", "Rua 2, Paripiranga, BA", commerce1);
			clientRepository.save(client);

			Client client3 = new Client("Mateus", birthDate, "12345678900",
			"15484787985", "Rua 3, São José dos Campos, SP", commerce2);

			clientRepository.save(client);
			clientRepository.save(client2);
			clientRepository.save(client3);

			OrderItem item = new OrderItem("Geladeira", 10, new BigDecimal("20000"), null);
			OrderItem item1 = new OrderItem("Playstation", 5, new BigDecimal("15000"), null);
			OrderItem item2 = new OrderItem("Mesa", 1, new BigDecimal("1500.00"), null);
			OrderItem item3 = new OrderItem("Shirt", 1, new BigDecimal("1500.00"), null);

			List<OrderItem> items = new ArrayList<>();
			items.add(item);
			items.add(item1);
			items.add(item2);

			// testing crud order

			Order order = new Order("CM200", new BigDecimal("220.50"), null, client, commerce, items);
			Order order1 = new Order("CM120", new BigDecimal("500.50"), null, client2, commerce1, items);
			Order order2 = new Order("CM350", new BigDecimal("700.50"), null, client3, commerce2, items);

			item.setOrder(order);
			item1.setOrder(order1);
			item2.setOrder(order2);

			orderRepository.save(order);
			orderRepository.save(order1);
			orderRepository.save(order2);

			orderItemRepository.save(item);
			orderItemRepository.save(item1);
			orderItemRepository.save(item2);

			// testing crud payment

			String dateFormat = "05/09/2023 17:03";
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			LocalDateTime date = LocalDateTime.parse(dateFormat, fmt);
			Payment payment = new Payment(PaymentType.BOLETO, "PG121", new BigDecimal("380.20"), 
			date, PaymentStatus.COMPLETED, client, order);

			Payment payment2 = new Payment(PaymentType.PIX, "PG511", new BigDecimal("400.20"), 
			date, PaymentStatus.COMPLETED, client2, order1);

			Payment payment3 = new Payment(PaymentType.BOLETO, "PG191", new BigDecimal("880.20"), 
			date, PaymentStatus.COMPLETED, client3, order2);

			paymentRepository.save(payment);
			paymentRepository.save(payment2);
			paymentRepository.save(payment3);

			orderRepository.save(order);
			orderRepository.save(order1);
			orderRepository.save(order2);

			// testing crud product

			Product product = new Product("PRD200", "Playstation", new BigDecimal("1800.00"), 
			2, Categories.ELETRONICS, commerce);
			Product product1 = new Product("PRD500", "Shirt", new BigDecimal("70.00"), 
			8, Categories.CLOTHING, commerce1);
			Product product2 = new Product("PRD700", "Bike", new BigDecimal("1200.00"), 
			1, Categories.SPORTING, commerce2);

			productRepository.save(product);
			productRepository.save(product1);
			productRepository.save(product2);
		};
	}
}
