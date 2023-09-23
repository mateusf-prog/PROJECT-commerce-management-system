package br.com.mateus.commercemanagementsystem.exceptions;

import java.io.Serial;

public class EntityInvalidDataException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EntityInvalidDataException(String message) {
        super(message);
    }
}
