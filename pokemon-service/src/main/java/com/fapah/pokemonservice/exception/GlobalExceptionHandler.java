package com.fapah.pokemonservice.exception;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = PokemonNotFoundException.class)
    public @ResponseBody ResponseEntity<ErrorResponse> handlePokemonNotFoundException(PokemonNotFoundException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = TypeNotFoundException.class)
    public @ResponseBody ResponseEntity<ErrorResponse> handleTypeNotFoundException(TypeNotFoundException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = CoachNotFoundException.class)
    public @ResponseBody ResponseEntity<ErrorResponse> handleCoachNotFoundException(CoachNotFoundException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = PokemonAlreadyHaveCoachException.class)
    public @ResponseBody ResponseEntity<ErrorResponse> handlePokemonAlreadyHaveCoachException(PokemonAlreadyHaveCoachException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.CONFLICT, ex.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.NOT_ACCEPTABLE, ex.getMessage()),
                HttpStatus.NOT_ACCEPTABLE
        );
    }

    @ExceptionHandler(MapToException.class)
    ResponseEntity<ErrorResponse> handleMapToException(MapToException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage()),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(TypeAlreadyExistException.class)
    ResponseEntity<ErrorResponse> handleTypeAlreadyExistException(TypeAlreadyExistException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.CONFLICT, ex.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(CoachAlreadyExistException.class)
    ResponseEntity<ErrorResponse> handleCoachAlreadyExistException(CoachAlreadyExistException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.CONFLICT, ex.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(PokemonAlreadyExistException.class)
    ResponseEntity<ErrorResponse> handlePokemonAlreadyExistException(PokemonAlreadyExistException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.CONFLICT, ex.getMessage()),
                HttpStatus.CONFLICT
        );
    }



    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ErrorResponse> handleUncaughtException(WebRequest request, RuntimeException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(
                errorResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private ErrorResponse errorResponseBuilder(HttpStatus status, String message) {
        log.info("Creating error response with status {} and message {}", status.value(), message);
        return ErrorResponse.builder()
                .statusCode(status.value())
                .timestamp(Instant.now())
                .message(message)
                .build();
    }
}
