package br.com.mateus.commercemanagementsystem.exceptions.order;

public class OrderNotContainClientException extends RuntimeException {

    private String message;

    public OrderNotContainClientException(String message) {
        super(message);
    }
}
