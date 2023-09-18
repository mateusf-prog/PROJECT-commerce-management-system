package br.com.mateus.commercemanagementsystem.exceptions.order;

public class AddOrRemoveOrderItemInvalidException extends RuntimeException {

    private String message;

    public AddOrRemoveOrderItemInvalidException(String message) {
        super(message);
    }
}
