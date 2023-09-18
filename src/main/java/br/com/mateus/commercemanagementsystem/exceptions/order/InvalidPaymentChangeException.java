package br.com.mateus.commercemanagementsystem.exceptions.order;

import lombok.experimental.SuperBuilder;

public class InvalidPaymentChangeException extends RuntimeException {

    private String message;

    public InvalidPaymentChangeException(String message) {
        super(message);
    }
}
