package br.com.mateus.commercemanagementsystem.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<StandardError> alreadyException(EntityAlreadyExistsException exc) {

        StandardError error = new StandardError(exc.getMessage(),409);

        return ResponseEntity.status(409).body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> notFoundException(EntityNotFoundException exc) {

        StandardError error = new StandardError(exc.getMessage(), 404);

        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(EntityMissingDependencyException.class)
    public ResponseEntity<StandardError> notContainException(EntityMissingDependencyException exc) {

        StandardError error = new StandardError(exc.getMessage(), 400);

        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(EntityInvalidDataException.class)
    public ResponseEntity<StandardError> invalidDataException(EntityInvalidDataException exc) {

        StandardError error = new StandardError(exc.getMessage(), 400);

        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(EntityInvalidCategoryException.class)
    public ResponseEntity<StandardError> invalidCategoryException(EntityInvalidCategoryException exc) {

        StandardError error = new StandardError(exc.getMessage(), 400);

        return ResponseEntity.status(400).body(error);
    }
}
