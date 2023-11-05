package br.com.mateus.commercemanagementsystem.exceptions;

import java.io.Serial;

public class CategoryNotExistsException extends RuntimeException {
    
    @Serial
    private static final long serialVersionUID = 1L;

    public CategoryNotExistsException(String message) {
        super(message);
    }
}
