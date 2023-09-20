package br.com.mateus.commercemanagementsystem.exceptions.orderItem;

public class InvalidQuantityOrderItemException extends RuntimeException {

    private String message;

    public InvalidQuantityOrderItemException(String message) {
        super(message);
    }
}
