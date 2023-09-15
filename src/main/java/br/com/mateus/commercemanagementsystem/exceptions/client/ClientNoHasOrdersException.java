package br.com.mateus.commercemanagementsystem.exceptions.client;

public class ClientNoHasOrdersException extends RuntimeException {

    private String message;

    public ClientNoHasOrdersException(String message) {
        super(message);
    }
}
