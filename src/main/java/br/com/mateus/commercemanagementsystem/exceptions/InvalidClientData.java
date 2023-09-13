package br.com.mateus.commercemanagementsystem.exceptions;

public class InvalidClientData extends RuntimeException {

    private String message;

    public InvalidClientData(String message) {
        super(message);
    }
}
