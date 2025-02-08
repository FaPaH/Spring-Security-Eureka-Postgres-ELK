package com.fapah.pokemonservice.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = PokemonNotFoundException.class)
    public @ResponseBody ResponseEntity<ErrorResponse> handlePokemonNotFoundException(PokemonNotFoundException ex) {
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = TypeNotFoundException.class)
    public @ResponseBody ResponseEntity<ErrorResponse> handleTypeNotFoundException(TypeNotFoundException ex) {
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = CoachNotFoundException.class)
    public @ResponseBody ResponseEntity<ErrorResponse> handleCoachNotFoundException(CoachNotFoundException ex) {
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = PokemonAlreadyHaveCoachException.class)
    public @ResponseBody ResponseEntity<ErrorResponse> handlePokemonAlreadyHaveCoachException(PokemonAlreadyHaveCoachException ex) {
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.CONFLICT, ex.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.NOT_ACCEPTABLE, ex.getMessage()),
                HttpStatus.NOT_ACCEPTABLE
        );
    }

    @ExceptionHandler(MapToException.class)
    ResponseEntity<ErrorResponse> handleMapToException(MapToException ex) {
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage()),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(TypeAlreadyExistException.class)
    ResponseEntity<ErrorResponse> handleTypeAlreadyExistException(TypeAlreadyExistException ex) {
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.CONFLICT, ex.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(CoachAlreadyExistException.class)
    ResponseEntity<ErrorResponse> handleCoachAlreadyExistException(CoachAlreadyExistException ex) {
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.CONFLICT, ex.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(PokemonAlreadyExistException.class)
    ResponseEntity<ErrorResponse> handlePokemonAlreadyExistException(PokemonAlreadyExistException ex) {
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.CONFLICT, ex.getMessage()),
                HttpStatus.CONFLICT
        );
    }



    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ErrorResponse> handleUncaughtException(WebRequest request, RuntimeException ex)
    {
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private ErrorResponse errorResponseBuilder(HttpStatus status, String message) {
        return ErrorResponse.builder()
                .statusCode(status.value())
                .timestamp(Instant.now())
                .message(message)
                .build();
    }
}
