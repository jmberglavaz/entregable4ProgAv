package uy.um.mascotas.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uy.um.mascotas.core.exception.MascotaNoEncontradaException;

import java.time.Instant;

@RestControllerAdvice
public class RESTExceptionHandler {

    @ExceptionHandler(MascotaNoEncontradaException.class)
    public ResponseEntity<APIError> handleNotFound(MascotaNoEncontradaException ex) {
        APIError error = new APIError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}