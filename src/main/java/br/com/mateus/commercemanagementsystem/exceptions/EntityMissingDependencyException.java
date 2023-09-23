package br.com.mateus.commercemanagementsystem.exceptions;

import java.io.Serial;

public class EntityMissingDependencyException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EntityMissingDependencyException(String message) {
        super(message);
    }
}
