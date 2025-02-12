package com.fapah.pokemonservice.exception;

import org.hibernate.MappingException;

public class MapToException extends MappingException {

    private static final long serialVersionUID = 4L;

    public MapToException(String message) {
        super(message);
    }
}
