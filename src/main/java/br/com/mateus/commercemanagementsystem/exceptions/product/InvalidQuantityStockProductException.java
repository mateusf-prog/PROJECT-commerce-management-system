package br.com.mateus.commercemanagementsystem.exceptions.product;

public class InvalidQuantityStockProductException extends RuntimeException {

    private String message;

    public InvalidQuantityStockProductException(String message) {
        super(message);
    }
}
