package br.com.mateus.commercemanagementsystem.exceptions.orderItem;

public class OrderItemNotFoundException extends RuntimeException {

    private String message;

    public OrderItemNotFoundException(String message) {
        super(message);
    }
}
