package br.com.mateus.commercemanagementsystem.exceptions.client;

public class ClientAlreadyExistsException extends RuntimeException {

    private String message;

    public ClientAlreadyExistsException(String message) {
        super(message);
    }
}
