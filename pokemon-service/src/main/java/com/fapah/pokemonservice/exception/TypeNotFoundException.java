package com.fapah.pokemonservice.exception;

public class TypeNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2L;

    public TypeNotFoundException(String message) {
        super(message);
    }
}
