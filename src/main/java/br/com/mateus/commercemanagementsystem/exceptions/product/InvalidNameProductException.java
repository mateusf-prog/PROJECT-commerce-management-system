package br.com.mateus.commercemanagementsystem.exceptions.product;

public class InvalidNameProductException extends RuntimeException {

    private String message;

    public InvalidNameProductException(String message) {
        super(message);
    }
}
