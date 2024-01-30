package br.com.mateus.commercemanagementsystem.exceptions;

import java.io.Serial;

public class UnauthorizedApiException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UnauthorizedApiException(String message) {
        super(message);
    }
}
