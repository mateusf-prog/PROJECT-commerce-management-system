package br.com.mateus.commercemanagementsystem.exceptions;

import java.io.Serial;

public class ExternalApiException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ExternalApiException(String message) {
        super(message);
    }
}