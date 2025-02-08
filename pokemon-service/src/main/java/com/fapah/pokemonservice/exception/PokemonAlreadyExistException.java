package com.fapah.pokemonservice.exception;

import org.springframework.dao.DataAccessException;

import java.sql.SQLException;

public class PokemonAlreadyExistException extends DataAccessException {

    private static final long serialVersionUID = 7L;

    public PokemonAlreadyExistException(String message) {
        super(message);
    }
}
