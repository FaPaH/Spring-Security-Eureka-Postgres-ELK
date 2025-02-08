package com.fapah.pokemonservice.exception;

public class PokemonAlreadyHaveCoachException extends RuntimeException {

    private static final long serialVersionUID = 3L;

    public PokemonAlreadyHaveCoachException(String message) {
        super(message);
    }
}
