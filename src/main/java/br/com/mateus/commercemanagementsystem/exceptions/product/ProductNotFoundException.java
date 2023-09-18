package br.com.mateus.commercemanagementsystem.exceptions.product;

public class ProductNotFoundException extends RuntimeException {

    private String message;

    public ProductNotFoundException(String message) {
        super(message);
    }
}
