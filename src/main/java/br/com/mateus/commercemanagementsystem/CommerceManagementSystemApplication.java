package br.com.mateus.commercemanagementsystem;

import br.com.mateus.commercemanagementsystem.model.*;
import br.com.mateus.commercemanagementsystem.model.enums.Categories;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import br.com.mateus.commercemanagementsystem.service.ClientService;
import br.com.mateus.commercemanagementsystem.service.serviceImpl.ClientServiceImpl;
import br.com.mateus.commercemanagementsystem.service.serviceImpl.OrderItemServiceImpl;
import br.com.mateus.commercemanagementsystem.service.serviceImpl.OrderServiceImpl;
import br.com.mateus.commercemanagementsystem.service.serviceImpl.ProductServiceImpl;
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
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class CommerceManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommerceManagementSystemApplication.class, args);
	}

	@Bean
    public CommandLineRunner myCommandLineRunner (OrderServiceImpl orderService, ClientService clientService,
												  PaymentRepository paymentRepository, OrderItemRepository orderItemRepository,
												  ProductServiceImpl productService, OrderItemServiceImpl orderItemService) {
        return (args) -> {

			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate birthdate = LocalDate.parse("15/12/1997", fmt);
			Client client = new Client("Mateus", birthdate, "44552211455",
					"56236521465", "endereço");

			Payment payment = new Payment(PaymentType.BOLETO, new BigDecimal("250.00"), LocalDateTime.now(),
					PaymentStatus.COMPLETED, null);

			OrderItem item = new OrderItem("Feijao", 10, new BigDecimal("10.00"), null);
			OrderItem item1 = new OrderItem("Arroz", 10, new BigDecimal("10.00"), null);
			OrderItem item2 = new OrderItem("Camisa", 10, new BigDecimal("10.00"), null);
			OrderItem item3 = new OrderItem("Alface", 10, new BigDecimal("10.00"), null);
			List<OrderItem> orderItems = new ArrayList<>();

			Order order = new Order(new BigDecimal("250.00"), payment, client, orderItems, LocalDateTime.now());

			payment.setOrder(order);

			//clientService.createClient(client);
			/*orderService.addItem(order, item);
			orderService.addItem(order, item1);
			orderService.addItem(order, item2);
			orderService.addItem(order, item3);
			item.setOrder(order);
			item1.setOrder(order);
			item2.setOrder(order);
			item3.setOrder(order);

			paymentRepository.save(payment);
			orderService.createOrder(order);

			orderItemRepository.save(item);
			orderItemRepository.save(item1);
			orderItemRepository.save(item2);
			orderItemRepository.save(item3);

			payment.setPaymentType(PaymentType.PIX);
			paymentRepository.save(payment);
			orderService.setPayment(order, payment);

			System.out.println("----REMOVENDO ITEMS----");
			orderService.removeItem(order, item2);
			orderItemRepository.delete(item2);*/


			Product product = new Product("Notebook", new BigDecimal("2000.00"), 25, Categories.ELETRONICS);
			/*Product product1 = new Product("Headset", new BigDecimal("80.00"), 95, Categories.ELETRONICS);
			Product product2 = new Product("Mousepad", new BigDecimal("50.00"), 80, Categories.ELETRONICS);
			Product product3 = new Product("Monitor", new BigDecimal("800.00"), 50, Categories.ELETRONICS);
			Product product4 = new Product("Smartphone", new BigDecimal("1800.00"), 30, Categories.ELETRONICS);


			productService.createProduct(product1);
			productService.createProduct(product2);
			productService.createProduct(product3);
			productService.createProduct(product4);*/

			OrderItem newItem = new OrderItem("Notebook", 0, product.getPrice(), order);

			System.out.println(orderItemService.validatePrice(newItem));
		};
	}
}
