package br.com.mateus.commercemanagementsystem.exceptions.order;

public class OrderNotFoundException extends RuntimeException {

    private String message;

    public OrderNotFoundException(String message) {
        super(message);
    }
}
