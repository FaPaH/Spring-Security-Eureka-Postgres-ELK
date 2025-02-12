package com.fapah.pokemonservice.exception;

import org.springframework.dao.DataAccessException;

import java.sql.SQLException;

public class TypeAlreadyExistException extends DataAccessException {

    private static final long serialVersionUID = 5L;

    public TypeAlreadyExistException(String message) {
        super(message);
    }
}
