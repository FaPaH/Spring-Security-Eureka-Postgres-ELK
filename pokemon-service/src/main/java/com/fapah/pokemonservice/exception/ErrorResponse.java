package com.fapah.pokemonservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private Integer statusCode;
    private String message;
    private Instant timestamp;

    public ErrorResponse(String message)
    {
        super();
        this.message = message;
    }
}
