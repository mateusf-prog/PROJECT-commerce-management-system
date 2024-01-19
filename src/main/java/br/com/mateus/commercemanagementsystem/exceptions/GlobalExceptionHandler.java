package br.com.mateus.commercemanagementsystem.exceptions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<StandardError> alreadyException(EntityAlreadyExistsException exc) {

        StandardError error = new StandardError(exc.getMessage(),409);

        return ResponseEntity.status(409).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> notFoundException(ResourceNotFoundException exc) {

        StandardError error = new StandardError(exc.getMessage(), 404);

        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(EntityInvalidDataException.class)
    public ResponseEntity<StandardError> invalidDataException(EntityInvalidDataException exc) {

        StandardError error = new StandardError(exc.getMessage(), 400);

        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(IntegrityViolationException.class)
    public ResponseEntity<StandardError> integrityViolation(IntegrityViolationException exc) {

        StandardError error = new StandardError(exc.getMessage(), 500);

        return ResponseEntity.status(500).body(error);
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<StandardError> unauthorizedAcccess(ExternalApiException exc) {

        StandardError error = new StandardError(exc.getMessage(), 401);

        return ResponseEntity.status(401).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException exc) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());

        List<String> errors = exc.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
