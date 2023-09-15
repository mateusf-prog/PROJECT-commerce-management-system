package br.com.mateus.commercemanagementsystem.exceptions.order;

public class OrderNotContainPaymentException extends RuntimeException {

    private String message;

    public OrderNotContainPaymentException(String message) {
        super(message);
    }
}
