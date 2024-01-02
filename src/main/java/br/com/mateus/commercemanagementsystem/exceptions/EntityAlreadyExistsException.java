package br.com.mateus.commercemanagementsystem.exceptions;

import java.io.Serial;

public class EntityAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
