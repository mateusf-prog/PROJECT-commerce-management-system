package br.com.mateus.commercemanagementsystem.exceptions.orderItem;

public class InvalidOrderItemNameException extends RuntimeException {

    private String message;

    public InvalidOrderItemNameException(String message) {
        super(message);
    }
}
