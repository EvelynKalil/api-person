package com.awsbackend.person_api.infrastructure.exceptionhandler;

import com.awsbackend.person_api.domain.exception.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String MESSAGE_ERROR = "Unexpected error occurred";
    // Validaciones de Bean Validation (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, HttpStatus.BAD_REQUEST.value());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        body.put(ERROR, errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Entidad no encontrada (404)
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePersonNotFound(PersonNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, HttpStatus.NOT_FOUND.value());
        body.put(ERROR, ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // Errores no controlados (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put(ERROR, MESSAGE_ERROR);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
