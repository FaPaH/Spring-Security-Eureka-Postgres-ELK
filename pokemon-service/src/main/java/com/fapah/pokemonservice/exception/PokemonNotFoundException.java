package com.fapah.pokemonservice.exception;

public class PokemonNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PokemonNotFoundException(String message) {
        super(message);
    }
}
