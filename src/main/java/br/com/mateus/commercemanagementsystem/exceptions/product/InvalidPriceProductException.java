package br.com.mateus.commercemanagementsystem.exceptions.product;

public class InvalidPriceProductException extends RuntimeException {

    private String message;

    public InvalidPriceProductException(String message) {
        super(message);
    }
}
