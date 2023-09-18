package br.com.mateus.commercemanagementsystem.exceptions.product;

public class InvalidCategoryProductException extends RuntimeException {

    private String message;

    public InvalidCategoryProductException(String message) {
        super(message);
    }
}
