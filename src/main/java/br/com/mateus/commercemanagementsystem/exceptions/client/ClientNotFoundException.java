package br.com.mateus.commercemanagementsystem.exceptions.client;

public class ClientNotFoundException extends RuntimeException {

    private String message;

    public ClientNotFoundException(String message) {
        super(message);
    }
}
