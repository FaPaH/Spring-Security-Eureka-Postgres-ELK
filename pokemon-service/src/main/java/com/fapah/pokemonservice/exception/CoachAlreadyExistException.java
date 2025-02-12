package com.fapah.pokemonservice.exception;

import org.springframework.dao.DataAccessException;

import java.sql.SQLException;

public class CoachAlreadyExistException extends DataAccessException {

    private static final long serialVersionUID = 6L;

    public CoachAlreadyExistException(String message) {
        super(message);
    }
}
