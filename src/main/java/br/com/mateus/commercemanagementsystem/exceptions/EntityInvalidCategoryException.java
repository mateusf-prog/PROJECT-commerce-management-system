package br.com.mateus.commercemanagementsystem.exceptions;

import java.io.Serial;

public class EntityInvalidCategoryException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EntityInvalidCategoryException(String message) {
        super(message);
    }
}
