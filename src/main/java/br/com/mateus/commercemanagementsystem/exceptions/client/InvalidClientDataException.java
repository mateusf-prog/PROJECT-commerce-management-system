package br.com.mateus.commercemanagementsystem.exceptions.client;

public class InvalidClientDataException extends RuntimeException {

    private String message;

    public InvalidClientDataException(String message) {
        super(message);
    }
}
