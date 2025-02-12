package com.fapah.pokemonservice.exception;

public class CoachNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3L;

    public CoachNotFoundException(String message) {
        super(message);
    }
}
