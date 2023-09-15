package br.com.mateus.commercemanagementsystem.exceptions.order;

public class OrderNotContainItemsException extends RuntimeException {

    private String message;

    public OrderNotContainItemsException(String message) {
        super(message);
    }
}
