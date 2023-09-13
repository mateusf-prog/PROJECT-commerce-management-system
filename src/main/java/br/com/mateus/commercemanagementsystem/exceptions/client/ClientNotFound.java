package br.com.mateus.commercemanagementsystem.exceptions.client;

public class ClientNotFound extends RuntimeException {

    private String message;

    public ClientNotFound(String message) {
        super(message);
    }
}
