package br.com.mateus.commercemanagementsystem.exceptions.orderItem;

public class InvalidPriceOrderItemException extends RuntimeException {

    private String message;

    public InvalidPriceOrderItemException(String message) {
        super(message);
    }
}
